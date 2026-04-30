package com.bookstore.order.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String userId;
    private String type;
    private LocalDateTime timestamp;
}