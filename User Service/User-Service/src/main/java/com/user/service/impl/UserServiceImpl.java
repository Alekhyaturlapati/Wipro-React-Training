package com.user.service.impl;

import com.user.entity.Contact;
import com.user.entity.User;
import com.user.external.ContactServiceFeingClient;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String CONTACT_SERVICE = "contactService";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactServiceFeingClient contactServiceClient;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            try {
                List<Contact> contacts = contactServiceClient.getContactsByUserId(user.getUserId());
                user.setContact(contacts);
            } catch (Exception e) {
                logger.error("Failed to fetch contacts for userId {}: {}", user.getUserId(), e.getMessage());
                user.setContact(Collections.emptyList()); // fallback manually
            }
        }
        return users;
    }

    @Override
    @CircuitBreaker(name = CONTACT_SERVICE, fallbackMethod = "getUserByIdFallback")
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        List<Contact> contacts = contactServiceClient.getContactsByUserId(id);
        user.setContact(contacts);
        return user;
    }

    // fallback method for getUserById
    public User getUserByIdFallback(Long id, Throwable ex) {
        logger.warn("Contact service unavailable for userId {}: {}", id, ex.getMessage());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        user.setContact(Collections.emptyList()); // empty contact list as fallback
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @CircuitBreaker(name = CONTACT_SERVICE, fallbackMethod = "getUserFallback")
    public User getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<Contact> contacts = contactServiceClient.getContactsByUserId(userId);
        user.setContact(contacts);
        return user;
    }

    // fallback method for getUser
    public User getUserFallback(Long userId, Throwable ex) {
        logger.warn("Contact service unavailable for userId {}: {}", userId, ex.getMessage());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        user.setContact(Collections.emptyList());
        return user;
    }
}
