package com.bookstore.notification.event;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserEvent {
    private String userId;
    private String email;
    private String type;
}