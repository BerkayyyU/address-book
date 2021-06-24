package com.example.application.repositories;

import com.example.application.models.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContactRepository extends CrudRepository<Contact,Long> {

}
