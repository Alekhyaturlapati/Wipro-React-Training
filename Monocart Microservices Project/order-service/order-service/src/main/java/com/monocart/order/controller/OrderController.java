package com.monocart.order.controller;

import com.monocart.order.entity.Order;
import com.monocart.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place an order
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    // Get orders by user
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    // Update order status
    @PutMapping("/{orderId}/status/{status}")
    public Order updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        return orderService.updateOrderStatus(orderId, status);
    }
}
