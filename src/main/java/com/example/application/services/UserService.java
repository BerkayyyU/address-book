package com.example.application.services;

import com.example.application.models.WebsiteUser;

import java.util.List;

public interface UserService {
    List<WebsiteUser> getUsers();
    WebsiteUser login(String email, String password);
    WebsiteUser save(WebsiteUser websiteUser);
    WebsiteUser getUserById(Long id);
}
