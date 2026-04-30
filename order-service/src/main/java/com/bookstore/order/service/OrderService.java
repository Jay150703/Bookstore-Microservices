package com.bookstore.order.service;

import com.bookstore.order.dto.*;
import com.bookstore.order.entity.*;
import com.bookstore.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public Order placeOrder(String userId, OrderRequest request) {
        List<OrderItem> items = request.getItems().stream().map(i -> OrderItem.builder()
                .productId(i.getProductId())
                .productTitle(i.getProductTitle())
                .quantity(i.getQuantity())
                .unitPrice(i.getUnitPrice())
                .build()).collect(Collectors.toList());

        BigDecimal total = items.stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .userId(userId)
                .totalAmount(total)
                .build();

        items.forEach(i -> i.setOrder(order));
        order.setItems(items);
        Order saved = orderRepository.save(order);
        eventPublisher.publishOrderPlaced(saved);
        return saved;
    }

    public List<Order> getUserOrders(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order cancelOrder(String userId, Long id) {
        Order order = getById(id);
        order.setStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateStatus(Long id, String status) {
        Order order = getById(id);
        order.setStatus(OrderStatus.valueOf(status));
        Order saved = orderRepository.save(order);
        eventPublisher.publishStatusChange(saved);
        return saved;
    }
}