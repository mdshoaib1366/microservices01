package com.nit.mybank.service;

import com.nit.mybank.constant.AccountStatus;
import com.nit.mybank.constant.AccountType;
import com.nit.mybank.dto.AccountResponse;
import com.nit.mybank.dto.CustomerRequest;
import com.nit.mybank.entity.Account;
import com.nit.mybank.entity.Customer;
import com.nit.mybank.mapper.AccountMapper;
import com.nit.mybank.mapper.CustomerMapper;
import com.nit.mybank.repository.AccountRepository;
import com.nit.mybank.repository.TransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // -------- Get account by account number --------
    @Cacheable(value = "ACCOUNT_BY_NUMBER_CACHE", key = "#accountNumber")
    public AccountResponse getByAccountNumber(String accountNumber) {
        System.out.println(">>> DB HIT accno<<<");
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.toDto(account);
    }

    // -------- Get all accounts for a customer --------
    @Cacheable(value = "ACCOUNT_BY_CUSTOMER_CACHE", key = "#customerId")
    public List<AccountResponse> getCustomerAccounts(UUID customerId) {
        System.out.println(">>> DB HIT Custno<<<");
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        List<AccountResponse> accountResponses = new ArrayList<>();
        for (Account account : accounts) {
            accountResponses.add(AccountMapper.toDto(account));
        }
        return accountResponses;
    }

    // -------- Create new account (optional feature) --------
    @CachePut(value = "ACCOUNT_REGISTER", key = "#result.id()")
    @Transactional
    public AccountResponse createAccount(CustomerRequest customerRequest, AccountType type) {
        System.out.println(">>> DB HIT CREATE<<<");
        Customer customer = CustomerMapper.toEntity(customerRequest);
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(type);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());

        Account saved = accountRepository.save(account);
        return AccountMapper.toDto(saved);
    }

    // -------- Block / deactivate account --------
    @CachePut(value = "ACCOUNT_BY_NUMBER", key = "#accountNumber")
    @Transactional
    public AccountResponse updateStatus(String accountNumber, AccountStatus status) {
        System.out.println(">>> DB HIT UPDATE<<<");
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setStatus(status);
        Account save = accountRepository.save(account);
        return AccountMapper.toDto(save);
    }

    // --------- Hardly Delete Account
    @CacheEvict(value = "ACCOUNT_BY_NUMBER", key = "#accountNumber")
    @Transactional
    public void deleteAccount(String accountNumber) {
        System.out.println(">>> DB HIT DELETE<<<");
        Optional<Account> acc = accountRepository.findByAccountNumber(accountNumber);
        if (acc.isPresent()) {
            Account account = acc.get();
            transactionRepository.deleteByAccountId(account.getId());
            accountRepository.deleteByAccountNumber(accountNumber);
        }else{
            throw new RuntimeException("Account not found");
        }

    }

    // -------- Helper --------
    private String generateAccountNumber() {
        return "ACC" + System.nanoTime();
    }
}
