package com.ecommerce;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class ProductServiceTest {
    @MockBean private ProductRepository repo;
    @Autowired private ProductService service;

    @Test
    void testAddProduct() {
        Product p = new Product(null, "Phone", "Smartphone", 500.0, 10);
        Mockito.when(repo.save(any(Product.class))).thenReturn(p);
        Product result = service.addProduct(p);
        assertEquals("Phone", result.getName());
    }
}
