package com.bookstore.admin.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String title;
    private String author;
    private String isbn;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private Long categoryId;
}