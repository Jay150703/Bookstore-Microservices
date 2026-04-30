package com.bookstore.wishlist.service;

import com.bookstore.wishlist.entity.*;
import com.bookstore.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final WishListRepository wishListRepository;

    private WishList getOrCreate(String userId) {
        return wishListRepository.findByUserId(userId)
                .orElseGet(() -> wishListRepository.save(
                        WishList.builder().userId(userId).build()));
    }

    public WishList getWishList(String userId) {
        return getOrCreate(userId);
    }

    public WishList addProduct(String userId, Long productId, String productTitle) {
        WishList wl = getOrCreate(userId);
        boolean exists = wl.getItems().stream().anyMatch(i -> i.getProductId().equals(productId));
        if (!exists) {
            WishListItem item = WishListItem.builder()
                    .productId(productId).productTitle(productTitle).wishList(wl).build();
            wl.getItems().add(item);
            wishListRepository.save(wl);
        }
        return wl;
    }

    public WishList removeProduct(String userId, Long productId) {
        WishList wl = getOrCreate(userId);
        wl.getItems().removeIf(i -> i.getProductId().equals(productId));
        return wishListRepository.save(wl);
    }

    public void clearWishList(String userId) {
        WishList wl = getOrCreate(userId);
        wl.getItems().clear();
        wishListRepository.save(wl);
    }
}