package com.auction.notification.kafka.order;

import java.math.BigDecimal;

public record Product(
        Long productId,
        String productName,
        String description,
        BigDecimal price,
        double quantity
) {
}
