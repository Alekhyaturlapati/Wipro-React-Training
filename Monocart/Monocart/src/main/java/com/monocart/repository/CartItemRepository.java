package com.monocart.repository;

import com.monocart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // Find all items of a specific user
    List<CartItem> findByUserId(Long userId);
}
