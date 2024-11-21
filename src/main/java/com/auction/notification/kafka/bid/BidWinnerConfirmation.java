package com.auction.notification.kafka.bid;

import com.auction.notification.kafka.order.Product;
import com.auction.notification.kafka.order.User;

import java.math.BigDecimal;

public record BidWinnerConfirmation(
        Long winningBidId,
        User user,
        Product product,
        BigDecimal bigAmount
        ) {
}
