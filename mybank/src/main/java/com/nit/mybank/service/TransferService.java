package com.nit.mybank.service;

import com.nit.mybank.constant.AccountStatus;
import com.nit.mybank.constant.TransactionStatus;
import com.nit.mybank.constant.TransactionType;
import com.nit.mybank.dto.TransferRequest;
import com.nit.mybank.entity.Account;
import com.nit.mybank.entity.Transaction;
import com.nit.mybank.repository.AccountRepository;
import com.nit.mybank.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void transferMoney(TransferRequest request) {

        // 1. Idempotency check (VERY IMPORTANT)
        Optional<Transaction> existingTx =
                transactionRepository.findByReferenceId(request.getReferenceId());

        if (existingTx.isPresent()) {
            throw new RuntimeException("Duplicate transaction request");
        }

        // 2. Lock both accounts (avoid race conditions)
        Account sender = accountRepository
                .findByAccountNumberForUpdate(request.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepository
                .findByAccountNumberForUpdate(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // 3. Validation
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid amount");
        }

        if (!sender.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new RuntimeException("Sender account is not active");
        }

        if (!receiver.getStatus().equals(AccountStatus.ACTIVE)) {
            throw new RuntimeException("Receiver account is not active");
        }

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // 4. Perform transfer
        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        // 5. Save accounts
        accountRepository.save(sender);
        accountRepository.save(receiver);

        // 6. Create transaction record
        Transaction tx = new Transaction();
        tx.setFromAccount(sender);
        tx.setToAccount(receiver);
        tx.setAmount(request.getAmount());
        tx.setTransactionType(TransactionType.TRANSFER);
        tx.setStatus(TransactionStatus.SUCCESS);
        tx.setReferenceId("TXN-"+ new Date().getTime());
        tx.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(tx);
    }
}