package com.example.application.services;

import com.example.application.models.WebsiteUser;
import com.example.application.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<WebsiteUser> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public WebsiteUser login(String email, String password) {
        List<WebsiteUser> websiteUser = userRepository.findByEmailAndPassword(email, password);
        if (websiteUser.size()==0){
            return new WebsiteUser();
        }
        return websiteUser.get(0);
    }

    @Override
    public WebsiteUser save(WebsiteUser websiteUser) {
        return userRepository.save(websiteUser);
    }

    @Override
    public WebsiteUser getUserById(Long id) {
        return userRepository.findUserById(id);
    }


}
