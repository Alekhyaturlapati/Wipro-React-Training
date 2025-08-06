package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    // Place an order
    @PostMapping
    public Order placeOrder(@RequestParam Long customerId,
                            @RequestParam Long productId,
                            @RequestParam int quantity) {
        return service.placeOrder(customerId, productId, quantity);
    }
}
