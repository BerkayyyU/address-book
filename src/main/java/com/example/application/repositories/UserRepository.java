package com.example.application.repositories;

import com.example.application.models.WebsiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<WebsiteUser,Long>, JpaRepository<WebsiteUser,Long> {
    List<WebsiteUser> findByEmailAndPassword(String email, String password);
    WebsiteUser findUserById(Long id);
}
