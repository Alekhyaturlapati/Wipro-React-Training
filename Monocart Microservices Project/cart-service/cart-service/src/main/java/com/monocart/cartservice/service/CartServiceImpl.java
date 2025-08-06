package com.monocart.cartservice.service;

import com.monocart.cartservice.entity.Cart;
import com.monocart.cartservice.entity.CartItem;
import com.monocart.cartservice.repository.CartRepository;
import com.monocart.cartservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setItems(new ArrayList<>());
        return cartRepository.save(cart);
    }

    @Override
    public Cart addItemToCart(Long cartId, CartItem item) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        item.setCart(cart);
        cartItemRepository.save(item);
        cart.getItems().add(item);
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.getItems().removeIf(ci -> ci.getId().equals(itemId));
        cartItemRepository.deleteById(itemId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }
}
