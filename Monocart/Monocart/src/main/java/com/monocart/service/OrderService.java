package com.monocart.service;

import com.monocart.entity.Order;
import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order updateOrder(Long id, Order order);
}
