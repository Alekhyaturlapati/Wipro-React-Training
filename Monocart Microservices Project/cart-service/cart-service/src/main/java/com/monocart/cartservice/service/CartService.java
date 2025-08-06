package com.monocart.cartservice.service;

import com.monocart.cartservice.entity.Cart;
import com.monocart.cartservice.entity.CartItem;

public interface CartService {
    Cart createCart(Long userId);
    Cart addItemToCart(Long cartId, CartItem item);
    Cart removeItemFromCart(Long cartId, Long itemId);
    Cart getCart(Long cartId);
}
