package com.auction.notification.kafka.bid;

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
