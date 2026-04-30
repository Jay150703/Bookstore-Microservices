package com.bookstore.order.service;

import com.bookstore.order.entity.Order;
import com.bookstore.order.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void publishOrderPlaced(Order order) {
        kafkaTemplate.send("order-events",
                new OrderEvent(order.getId(), order.getUserId(), "ORDER_PLACED", LocalDateTime.now()));
    }

    public void publishStatusChange(Order order) {
        kafkaTemplate.send("order-events",
                new OrderEvent(order.getId(), order.getUserId(), "ORDER_" + order.getStatus().name(), LocalDateTime.now()));
    }
}