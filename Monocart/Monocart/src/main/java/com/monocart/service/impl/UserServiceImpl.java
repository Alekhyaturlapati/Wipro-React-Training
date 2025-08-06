package com.monocart.service.impl;

import com.monocart.entity.User;
import com.monocart.repository.UserRepository;
import com.monocart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired private UserRepository repo;

    public User create(User user) { return repo.save(user); }
    public List<User> getAll() { return repo.findAll(); }
}
