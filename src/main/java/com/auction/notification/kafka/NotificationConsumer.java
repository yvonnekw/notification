package com.auction.notification.kafka;

import com.auction.notification.email.EmailService;
import com.auction.notification.kafka.bid.BidWinnerConfirmation;
import com.auction.notification.kafka.order.OrderConfirmation;
import com.auction.notification.kafka.payment.PaymentConfirmation;
import com.auction.notification.kafka.payment.PaymentNotificationRequest;
import com.auction.notification.notification.Notification;
import com.auction.notification.notification.NotificationRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.auction.notification.notification.NotificationType.*;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic", groupId = "paymentGroup")
    public void consumePaymentSuccessNotification(PaymentNotificationRequest paymentConfirmation) throws MessagingException {
        try {
            log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
            notificationRepository.save(
                    Notification.builder()
                            .type(PAYMENT_CONFIRMATION)
                            .notificationDate(LocalDateTime.now())
                            .paymentConfirmation((paymentConfirmation))
                            .build()
            );

           // var userFullName = paymentConfirmation.userFirstName() + " " + paymentConfirmation.userLastName();
            emailService.sendPaymentSuccessEmail(
                    paymentConfirmation.orderReference(),
                    paymentConfirmation.totalAmount(),
                    paymentConfirmation.paymentMethod(),
                    paymentConfirmation.username(),
                    paymentConfirmation.userFirstName(),
                    paymentConfirmation.userLastName(),
                    paymentConfirmation.userEmail()
            );

        } catch (Exception e) {
            log.error("Failed to process payment success notification for order: {}", paymentConfirmation.orderReference(), e);
        }
    }

    @KafkaListener(topics = "order-topic", groupId = "orderGroup")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        try {
            log.info(format("Consuming the message from order-topic Topic:: %s", orderConfirmation));
            notificationRepository.save(
                    Notification.builder()
                            .type(ORDER_CONFIRMATION)
                            .notificationDate(LocalDateTime.now())
                            .orderConfirmation((orderConfirmation))
                            .build()
            );
            //var userFullName = orderConfirmation.userFirstName() + " " + paymentConfirmation.userLastName();
            emailService.sendOrderConfirmationEmail(
                    orderConfirmation.email(),
                    orderConfirmation.username(),
                    orderConfirmation.firstName(),
                    orderConfirmation.lastName(),
                    orderConfirmation.totalAmount(),
                    orderConfirmation.orderReference(),
                    orderConfirmation.paymentMethod(),
                    orderConfirmation.products()
            );

        } catch (Exception e) {
            log.error("Failed to process order notification for order: {}", orderConfirmation.orderReference(), e);
        }
    }

    @KafkaListener(topics = "bid-winner-topic" , groupId = "bidGroup")
    public void consumeBidWinnerConfirmationNotification(BidWinnerConfirmation bidWinnerConfirmation) throws MessagingException {
        try {
            log.info(format("Consuming the message from bid-winner-topic Topic:: %s", bidWinnerConfirmation));
            notificationRepository.save(
                    Notification.builder()
                            .type(BID_WINNER_CONFIRMATION)
                            .notificationDate(LocalDateTime.now())
                            .bidWinnerConfirmation((bidWinnerConfirmation))
                            .build()
            );
            var userFullName = bidWinnerConfirmation.user().firstName() + " " + bidWinnerConfirmation.user().lastName();
            emailService.sendBidWinnerConfirmationEmail(
                    bidWinnerConfirmation.user().emailAddress(),
                    userFullName,
                    bidWinnerConfirmation.bigAmount(),
                    bidWinnerConfirmation.winningBidId(),
                    bidWinnerConfirmation.product()

            );


        } catch (Exception e) {
            log.error("Failed to process bid winner notification: {}", bidWinnerConfirmation.winningBidId(), e);
        }
    }
}
