package com.monocart.service.impl;

import com.monocart.entity.CartItem;
import com.monocart.repository.CartItemRepository;
import com.monocart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemRepository repo;

    @Override
    public CartItem addToCart(CartItem item) {
        return repo.save(item);
    }

    @Override
    public List<CartItem> getCartByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public void removeFromCart(Long cartId) {
        repo.deleteById(cartId);
    }
}
