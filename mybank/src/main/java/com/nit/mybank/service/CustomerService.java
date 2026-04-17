package com.nit.mybank.service;

import com.nit.mybank.constant.AccountStatus;
import com.nit.mybank.constant.AccountType;
import com.nit.mybank.dto.AuthResponse;
import com.nit.mybank.dto.LoginRequest;
import com.nit.mybank.dto.RegisterRequest;
import com.nit.mybank.entity.Account;
import com.nit.mybank.entity.Customer;
import com.nit.mybank.repository.AccountRepository;
import com.nit.mybank.repository.CustomerRepository;
import com.nit.mybank.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public CustomerService(CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- REGISTER ----------------
    public void register(RegisterRequest request) {

        // 1. Check if user already exists
        customerRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            throw new RuntimeException("Email already registered");
        });

        // 2. Create customer
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.setCreatedAt(LocalDateTime.now());

        customerRepository.save(customer);

        // 3. Create default account
        Account account = new Account();
        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(AccountType.SAVINGS);
        account.setStatus(AccountStatus.ACTIVE);
        account.setCreatedAt(LocalDateTime.now());

        accountRepository.save(account);
    }

    // ---------------- LOGIN ----------------
    public AuthResponse login(LoginRequest request) {

        Customer customer = customerRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 2. Validate password
        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // 3. Generate JWT (placeholder for now)
        String token = jwtUtil.generateToken(customer.getEmail());

        AuthResponse response = new AuthResponse();
        response.setToken(token);
        return response;
    }

    // ---------------- HELPERS ----------------
    private String generateAccountNumber() {
        return "ACC" + System.currentTimeMillis();
    }

}