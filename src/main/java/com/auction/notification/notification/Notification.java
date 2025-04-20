package com.auction.notification.notification;

import com.auction.notification.kafka.bid.BidWinnerConfirmation;
import com.auction.notification.kafka.order.OrderConfirmation;
import com.auction.notification.kafka.payment.PaymentConfirmation;
import com.auction.notification.kafka.payment.PaymentNotificationRequest;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime notificationDate;
    private OrderConfirmation orderConfirmation;
    private PaymentNotificationRequest paymentConfirmation;
    private BidWinnerConfirmation bidWinnerConfirmation;
}
