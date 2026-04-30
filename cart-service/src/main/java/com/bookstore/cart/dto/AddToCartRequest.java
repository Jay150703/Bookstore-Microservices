package com.bookstore.cart.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AddToCartRequest {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal unitPrice;
}