package com.auction.notification.kafka.bid;

import com.auction.notification.kafka.order.Product;
import com.auction.notification.kafka.order.User;
import com.auction.notification.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidWinnerConfirmation(
        Long winningBidId,
        Long bidId,
        String username,
        String userFirstName,
        String userLastName,
        String userEmail,
        BigDecimal bidAmount,
        LocalDateTime bidTime,
        Long productId,
        String productName,
        String brandName,
        String description
        ) {
}
