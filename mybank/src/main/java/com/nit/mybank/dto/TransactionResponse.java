package com.nit.mybank.dto;

import com.nit.mybank.constant.TransactionStatus;
import com.nit.mybank.constant.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private UUID id;
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus status;
    private String referenceId;
    private LocalDateTime createdAt;
}
