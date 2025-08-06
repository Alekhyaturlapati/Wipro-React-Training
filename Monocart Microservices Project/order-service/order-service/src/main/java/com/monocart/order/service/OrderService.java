package com.monocart.order.service;

import com.monocart.order.entity.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getOrdersByUser(Long userId);
    Order updateOrderStatus(Long orderId, String status);
}
