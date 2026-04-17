package com.nit.mybank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String referenceId; // for idempotency
}