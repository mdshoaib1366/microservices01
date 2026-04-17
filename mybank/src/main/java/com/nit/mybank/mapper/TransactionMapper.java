package com.nit.mybank.mapper;

import com.nit.mybank.dto.TransactionResponse;
import com.nit.mybank.entity.Transaction;

public class TransactionMapper {
    public static TransactionResponse mapToResponse(Transaction tx) {

        return TransactionResponse.builder()
                .id(tx.getId())
                .fromAccountNumber(
                        tx.getFromAccount() != null
                                ? tx.getFromAccount().getAccountNumber()
                                : null
                )
                .toAccountNumber(
                        tx.getToAccount() != null
                                ? tx.getToAccount().getAccountNumber()
                                : null
                )
                .amount(tx.getAmount())
                .transactionType(tx.getTransactionType())
                .status(tx.getStatus())
                .referenceId(tx.getReferenceId())
                .createdAt(tx.getCreatedAt())
                .build();
    }
}
