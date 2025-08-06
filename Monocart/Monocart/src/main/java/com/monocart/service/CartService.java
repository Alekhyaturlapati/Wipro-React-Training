package com.monocart.service;

import com.monocart.entity.CartItem;
import java.util.List;

public interface CartService {
    CartItem addToCart(CartItem item);
    List<CartItem> getCartByUser(Long userId);
    void removeFromCart(Long cartId);
}
