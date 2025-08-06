package com.monocart.service;

import com.monocart.entity.User;
import java.util.List;

public interface UserService {
    User create(User user);
    List<User> getAll();
}
