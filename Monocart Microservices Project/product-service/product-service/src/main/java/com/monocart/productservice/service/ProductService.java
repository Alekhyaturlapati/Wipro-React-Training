package com.monocart.productservice.service;

import com.monocart.productservice.entity.Product;
import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    void deleteProduct(Long id);
}
