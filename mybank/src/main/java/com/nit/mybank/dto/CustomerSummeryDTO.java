package com.nit.mybank.dto;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CustomerSummeryDTO {
    private UUID customerId;
    private String customerName;
    private String customerEmail;
}
