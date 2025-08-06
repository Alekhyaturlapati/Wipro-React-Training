package com.ecommerce.service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public Product addProduct(Product product) {
        return repo.save(product);
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product updateProduct(Long id, Product updated) {
        Product p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        p.setName(updated.getName());
        p.setDescription(updated.getDescription());
        p.setPrice(updated.getPrice());
        p.setQuantity(updated.getQuantity());
        return repo.save(p);
    }

    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }
}
