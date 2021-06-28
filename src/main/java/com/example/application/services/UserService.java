package com.example.application.services;

import com.example.application.models.User;

public interface UserService {
    User login(String email, String password);
    User save(User user);

}
