package com.ecommerce.controller;

import com.ecommerce.model.Customer;
import com.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepository repo;

    // ✅ Create new Customer
    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return repo.save(customer);
    }

    // ✅ Get all Customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    // ✅ Get Customer by ID
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }

    // ✅ Update Customer
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Customer customer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
        customer.setName(updatedCustomer.getName());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setAddress(updatedCustomer.getAddress());
        return repo.save(customer);
    }

    // ✅ Delete Customer
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Customer not found with ID: " + id);
        }
        repo.deleteById(id);
        return "Customer with ID " + id + " deleted successfully!";
    }
}
