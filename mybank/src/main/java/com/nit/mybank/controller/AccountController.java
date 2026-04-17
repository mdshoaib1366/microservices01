package com.nit.mybank.controller;

import com.nit.mybank.constant.AccountStatus;
import com.nit.mybank.constant.AccountType;
import com.nit.mybank.dto.AccountResponse;
import com.nit.mybank.dto.CustomerRequest;
import com.nit.mybank.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getByAccountNumber(accountNumber));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountResponse>> getAccountsByCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(accountService.getCustomerAccounts(customerId));
    }

    @PostMapping("/register")
    public ResponseEntity<AccountResponse> registerAccount(@RequestBody CustomerRequest request,
                                                           @RequestParam AccountType type) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(request, type));
    }

    @PutMapping("/update")
    public ResponseEntity<AccountResponse> updateAccountStatus(@RequestParam(required = false) AccountStatus status,
                                                      @RequestParam String accountNumber){
        AccountResponse response = accountService.updateStatus(accountNumber, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok("Successfully deleted account");
    }
}

