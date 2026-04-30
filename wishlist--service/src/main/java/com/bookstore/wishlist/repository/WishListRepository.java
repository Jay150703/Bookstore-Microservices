package com.bookstore.wishlist.repository;

import com.bookstore.wishlist.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUserId(String userId);
}