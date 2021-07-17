package com.example.application.services;

import com.example.application.models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User login(String email, String password);
    User save(User user);
    User getUserById(Long id);
}
