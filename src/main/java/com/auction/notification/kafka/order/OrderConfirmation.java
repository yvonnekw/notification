package com.auction.notification.kafka.order;

import com.auction.notification.kafka.payment.PaymentMethod;
import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String username,
        String firstName,
        String lastName,
        String email,
        List<Product> products
) {
}
