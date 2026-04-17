package com.example.stripepayment.dtos;

import com.example.stripepayment.constants.PaymentStatus;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class StripeResponse {
    private PaymentStatus status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}
