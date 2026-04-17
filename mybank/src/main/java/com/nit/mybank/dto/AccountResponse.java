package com.nit.mybank.dto;

import com.nit.mybank.constant.AccountStatus;
import com.nit.mybank.constant.AccountType;
import com.nit.mybank.entity.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class AccountResponse {
    private UUID id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountType accountType;
    private AccountStatus status;
    private Long version;
    private LocalDateTime createdAt;

    // Flattened customer info (safe)
    private CustomerSummeryDTO customerDto;
}
