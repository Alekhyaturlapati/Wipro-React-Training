package com.user.service.impl;

import com.user.entity.Contact;
import com.user.entity.User;
import com.user.external.ContactServiceFeingClient;
import com.user.repository.UserRepository;
import com.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

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
            List<Contact> contacts = contactServiceClient.getContactsByUserId(user.getUserId());
            user.setContact(contacts);
        }
        return users;
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        List<Contact> contacts = contactServiceClient.getContactsByUserId(id);
        user.setContact(contacts);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<Contact> contacts = contactServiceClient.getContactsByUserId(userId);
        user.setContact(contacts);
        return user;
    }
}
