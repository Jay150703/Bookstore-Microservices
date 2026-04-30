package com.bookstore.cart.service;

import com.bookstore.cart.dto.AddToCartRequest;
import com.bookstore.cart.model.*;
import com.bookstore.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCart(String userId) {
        return cartRepository.findById(userId).orElse(new Cart(userId, java.util.Collections.emptyList(), BigDecimal.ZERO));
    }

    public Cart addToCart(String userId, AddToCartRequest request) {
        Cart cart = cartRepository.findById(userId).orElse(new Cart(userId, new java.util.ArrayList<>(), BigDecimal.ZERO));
        Optional<CartItem> existing = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId())).findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + request.getQuantity());
        } else {
            cart.getItems().add(new CartItem(request.getProductId(), request.getProductTitle(),
                    request.getQuantity(), request.getUnitPrice()));
        }
        recalculate(cart);
        return cartRepository.save(cart);
    }

    public Cart updateItem(String userId, Long productId, int quantity) {
        Cart cart = getCart(userId);
        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
        recalculate(cart);
        return cartRepository.save(cart);
    }

    public void removeItem(String userId, Long productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        recalculate(cart);
        cartRepository.save(cart);
    }

    public void clearCart(String userId) {
        Cart cart = getCart(userId);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    public BigDecimal getTotal(String userId) {
        return getCart(userId).getTotalAmount();
    }

    private void recalculate(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}