package com.nit.mybank.service;

import com.nit.mybank.dto.TransactionResponse;
import com.nit.mybank.entity.Account;
import com.nit.mybank.entity.Transaction;
import com.nit.mybank.mapper.TransactionMapper;
import com.nit.mybank.repository.AccountRepository;
import com.nit.mybank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionResponse> getTransactionsByAccount(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return transactionRepository.findAll().stream()
                .filter(tx ->
                        tx.getFromAccount().getId().equals(account.getId()) ||
                                tx.getToAccount().getId().equals(account.getId()))
                .map(TransactionMapper::mapToResponse).toList();
    }
}