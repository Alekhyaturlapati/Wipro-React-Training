package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "`order`")   
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private int quantity;
    private LocalDateTime orderDate;
    private String status;

    // ✅ custom constructor without ID (so you can create easily)
    public Order(Customer customer, Product product, int quantity, LocalDateTime orderDate, String status) {
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
    }
}
