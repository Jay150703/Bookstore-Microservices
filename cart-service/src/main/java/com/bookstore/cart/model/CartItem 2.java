package com.bookstore.cart.model;

import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class CartItem implements Serializable {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal unitPrice;
}