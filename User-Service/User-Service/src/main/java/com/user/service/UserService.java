package com.user.service;

import com.user.entity.User;
import java.util.List;

public interface UserService {

    User saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);

    // Added for specific controller mapping
    User getUser(Long userId);
}
