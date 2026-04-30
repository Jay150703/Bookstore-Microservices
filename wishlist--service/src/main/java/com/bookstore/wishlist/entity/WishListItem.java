package com.bookstore.wishlist.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "wishlist_items")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WishListItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productTitle;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private WishList wishList;
}