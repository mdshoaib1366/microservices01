package com.example.stripepayment.dtos;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class PaymentRequest {
    private String productName;
    private String productCurrency;
    private long productAmount;
    private long productQuantity;
}
