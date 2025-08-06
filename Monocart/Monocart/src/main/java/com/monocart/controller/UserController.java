package com.monocart.controller;

import com.monocart.entity.User;
import com.monocart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired private UserService service;

    @PostMapping
    public User create(@RequestBody User user) { return service.create(user); }

    @GetMapping
    public List<User> getAll() { return service.getAll(); }
}
