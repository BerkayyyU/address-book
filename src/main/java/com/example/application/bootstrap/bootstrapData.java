package com.example.application.bootstrap;

import com.example.application.models.WebsiteUser;
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
        WebsiteUser websiteUser1 = new WebsiteUser();
        websiteUser1.setEmail("berkay");
        websiteUser1.setPassword("123");
        websiteUser1.setName("Berkayyy");
        userService.save(websiteUser1);
    }
}
