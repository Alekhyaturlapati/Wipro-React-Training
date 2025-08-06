package com.monocart.cartservice.controller;

import com.monocart.cartservice.entity.Cart;
import com.monocart.cartservice.entity.CartItem;
import com.monocart.cartservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public Cart createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }

    @PostMapping("/{cartId}/items")
    public Cart addItem(@PathVariable Long cartId, @RequestBody CartItem item) {
        return cartService.addItemToCart(cartId, item);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public Cart removeItem(@PathVariable Long cartId, @PathVariable Long itemId) {
        return cartService.removeItemFromCart(cartId, itemId);
    }

    @GetMapping("/{cartId}")
    public Cart getCart(@PathVariable Long cartId) {
        return cartService.getCart(cartId);
    }
}
