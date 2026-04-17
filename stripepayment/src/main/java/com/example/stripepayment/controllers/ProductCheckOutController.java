package com.example.stripepayment.controllers;

import com.example.stripepayment.dtos.PaymentRequest;
import com.example.stripepayment.dtos.StripeResponse;
import com.example.stripepayment.services.StripeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/v1")
public class ProductCheckOutController {

    private final StripeService stripeService;

    public ProductCheckOutController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkout(@RequestBody PaymentRequest paymentRequest) {
        StripeResponse stripeResponse = stripeService.checkOutProducts(paymentRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stripeResponse);
    }
}
