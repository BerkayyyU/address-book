package com.example.application.bootstrap;

import com.example.application.models.User;
import com.example.application.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrapData implements CommandLineRunner {
    private final UserService userService;

    public bootstrapData(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setEmail("berkay");
        user1.setPassword("123");
        user1.setName("Berkayyy");
        userService.save(user1);
    }
}
