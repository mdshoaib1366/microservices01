package com.example.stripepayment.services;

import com.example.stripepayment.constants.PaymentStatus;
import com.example.stripepayment.dtos.PaymentRequest;
import com.example.stripepayment.dtos.StripeResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret-key}")
    private String secretKey;

    public StripeResponse checkOutProducts(PaymentRequest paymentRequest) {

        Stripe.apiKey = secretKey;
        // product name
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(paymentRequest.getProductName())
                        .build();

        // product currency and unit(smallest unit of currency)
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setProductData(productData)
                .setCurrency(paymentRequest.getProductCurrency() == null ? "USD" : paymentRequest.getProductCurrency())
                .setUnitAmount(paymentRequest.getProductAmount())
                .build();

        // product quantity
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(paymentRequest.getProductQuantity())
                .setPriceData(priceData)
                .build();

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://localhost:8080/v1/success")
                .setCancelUrl("https://localhost:8080/v1/cancel")
                .addLineItem(lineItem)
                .build();

        Session session = null;
        PaymentStatus paymentStatus = null;

        try {
            session = Session.create(params);
            paymentStatus = PaymentStatus.SUCCEEDED;
        } catch (StripeException e) {
            System.out.println("Error while creating Stripe Session: " + e.getMessage());
            paymentStatus = PaymentStatus.FAILED;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(session != null) {
            session.getId();
        }
        else {
            throw new RuntimeException("Stripe session creation failed");
        }
        return StripeResponse.builder()
                .message("Successfully created Stripe Session")
                .status(paymentStatus)
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();
    }
}
