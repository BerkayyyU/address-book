package com.example.application.repositories;

import com.example.application.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Long>, JpaRepository<User,Long> {
    List<User> findByEmailAndPassword(String email, String password);
    User  findUserById(Long id);
}
