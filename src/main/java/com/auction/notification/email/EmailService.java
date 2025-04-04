package com.auction.notification.email;

import com.auction.notification.kafka.order.Product;
import com.auction.notification.kafka.payment.PaymentMethod;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auction.notification.email.EmailTemplates.*;

import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(
            String orderReference,
            BigDecimal amount,
            PaymentMethod paymentMethod,
            String username,
            String userFirstName,
            String userLastName,
            String destinationEmail
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@auction.com");
        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderReference", orderReference);
        variables.put("totalAmount", amount);
        variables.put("paymentMethod", paymentMethod);
        variables.put("username", username);
        variables.put("userFirstName", userFirstName);
        variables.put("userLastName", userLastName);
        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email Successfully send to %s with template %s, ", destinationEmail, templateName));
        } catch (MessagingException e) {
          log.warn("WARNING - Cannot send email to {} ", destinationEmail);
        }
    }

    @Async
    public void sendOrderConfirmationEmail(
            String destinationEmail,
            String username,
            String firstName,
            String lastName,
            BigDecimal amount,
            String orderReference,
            PaymentMethod paymentMethod,
            List<Product> products
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@auction.com");
        final String templateName = ORDER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", username);
        variables.put("firstName", firstName);
        variables.put("lastName", lastName);
        variables.put("totalAmount", amount);
        variables.put("orderReference", orderReference);
        variables.put("paymentMethod", paymentMethod);
        variables.put("products", products);
        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email Successfully send to %s with template %s, ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send email to {} ", destinationEmail);
        }
    }
    @Async
    public void sendBidWinnerConfirmationEmail(
            Long winningBidId,
            Long bidId,
            String username,
            String userFirstName,
            String userLastName,
            String destinationEmail,
            BigDecimal bidAmount,
            LocalDateTime bidTime,
            Long productId,
            String productName,
            String brandName,
            String description
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@auction.com");
        final String templateName = BID_WINNER_CONFIRMATION.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("winningBidId", winningBidId);
        variables.put("bidId", bidId);
        variables.put("username", username);
        variables.put("userFirstName", userFirstName);
        variables.put("userLastName", userLastName);
        variables.put("destinationEmail", destinationEmail);
        variables.put("bidAmount", bidAmount);
        variables.put("bidTime", bidTime);
        variables.put("productId", productId);
        variables.put("productName", productName);
        variables.put("brandName", brandName);
        variables.put("description", description);
        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(ORDER_CONFIRMATION.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(format("INFO - Email Successfully send to %s with template %s, ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send email to {} ", destinationEmail);
        }
    }

}
