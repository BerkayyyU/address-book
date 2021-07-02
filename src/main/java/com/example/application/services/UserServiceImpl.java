package com.example.application.services;

import com.example.application.models.User;
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
    public User login(String email, String password) {
        List<User> user = userRepository.findByEmailAndPassword(email, password);
        if (user.size()==0){
            return new User();
        }
        return  user.get(0);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }


}
