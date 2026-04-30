package com.bookstore.wishlist.controller;

import com.bookstore.wishlist.entity.WishList;
import com.bookstore.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @GetMapping
    public ResponseEntity<WishList> get(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(wishListService.getWishList(userId));
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<WishList> add(@RequestHeader("userId") String userId,
                                        @PathVariable Long productId,
                                        @RequestParam String productTitle) {
        return ResponseEntity.ok(wishListService.addProduct(userId, productId, productTitle));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<WishList> remove(@RequestHeader("userId") String userId,
                                           @PathVariable Long productId) {
        return ResponseEntity.ok(wishListService.removeProduct(userId, productId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear(@RequestHeader("userId") String userId) {
        wishListService.clearWishList(userId);
        return ResponseEntity.noContent().build();
    }
}