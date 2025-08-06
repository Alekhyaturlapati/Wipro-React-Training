package com.monocart.service.impl;

import com.monocart.entity.Order;
import com.monocart.repository.OrderRepository;
import com.monocart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repo;

    @Override
    public Order placeOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        return repo.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    @Override
    public Order updateOrder(Long id, Order order) {
        Order existing = repo.findById(id).orElse(null);
        if (existing != null) {
            existing.setStatus(order.getStatus());
            existing.setTotalAmount(order.getTotalAmount());
            return repo.save(existing);
        }
        return null;
    }
}
