package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    // Create product
    @PostMapping
    public Product add(@RequestBody Product p) {
        return service.addProduct(p);
    }

    // Get all products
    @GetMapping
    public List<Product> getAll() {
        return service.getAll();
    }

    // Update product
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product p) {
        return service.updateProduct(id, p);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}
