package com.bookstore.notification.service;

import com.bookstore.notification.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOrderConfirmation(OrderEvent event) {
        log.info("Sending order confirmation for order: {}", event.getOrderId());
        // In production, use actual email. For dev, just log.
    }

    public void sendShippingUpdate(OrderEvent event) {
        log.info("Sending shipping update for order: {}", event.getOrderId());
    }

    public void sendDeliveryConfirmation(OrderEvent event) {
        log.info("Sending delivery confirmation for order: {}", event.getOrderId());
    }

    public void sendWelcomeEmail(UserEvent event) {
        log.info("Sending welcome email to: {}", event.getEmail());
    }
}