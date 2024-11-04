package com.auction.notification.kafka.order;

public record User(
        Long userId,
        String firstName,
        String lastName,
        String emailAddress
) {
}
