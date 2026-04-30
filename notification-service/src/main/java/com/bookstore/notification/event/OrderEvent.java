package com.bookstore.notification.event;

import lombok.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private String userId;
    private String type;
    private LocalDateTime timestamp;
}