package com.bookstore.cart.controller;

import com.bookstore.cart.dto.AddToCartRequest;
import com.bookstore.cart.model.Cart;
import com.bookstore.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> add(@RequestHeader("userId") String userId,
                                    @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(userId, request));
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> update(@RequestHeader("userId") String userId,
                                       @RequestParam Long productId,
                                       @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItem(userId, productId, quantity));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<Void> remove(@RequestHeader("userId") String userId,
                                       @PathVariable Long productId) {
        cartService.removeItem(userId, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear(@RequestHeader("userId") String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> total(@RequestHeader("userId") String userId) {
        return ResponseEntity.ok(cartService.getTotal(userId));
    }
}