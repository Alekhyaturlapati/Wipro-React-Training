package com.monocart.controller;

import com.monocart.entity.CartItem;
import com.monocart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService service;

    // Add item to cart
    @PostMapping
    public CartItem addToCart(@RequestBody CartItem item) {
        return service.addToCart(item);
    }

    // Get cart by user
    @GetMapping("/{userId}")
    public List<CartItem> getCartByUser(@PathVariable Long userId) {
        return service.getCartByUser(userId);
    }

    // Remove item from cart
    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable Long cartId) {
        service.removeFromCart(cartId);
        return "Item removed from cart!";
    }
}
