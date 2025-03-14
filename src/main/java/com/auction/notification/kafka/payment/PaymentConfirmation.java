package com.auction.notification.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String username,
        String userFirstName,
        String userLastName,
        String userEmail
) {
}
