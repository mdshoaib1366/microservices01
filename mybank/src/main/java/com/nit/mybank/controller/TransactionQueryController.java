package com.nit.mybank.controller;

import com.nit.mybank.dto.TransactionResponse;
import com.nit.mybank.entity.Transaction;
import com.nit.mybank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionQueryController {

    private final TransactionService transactionService;

    public TransactionQueryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionResponse>> getTransactions(
            @PathVariable String accountNumber) {

        return ResponseEntity.ok(
                transactionService.getTransactionsByAccount(accountNumber)
        );
    }
}
