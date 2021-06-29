package com.example.application.repositories;

import com.example.application.models.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ContactRepository extends CrudRepository<Contact,Long> {
    //Optional<Phone> findContactByPhoneId(Long id);
    List<Contact> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    List<Contact> findByUserId(Long id);

}
