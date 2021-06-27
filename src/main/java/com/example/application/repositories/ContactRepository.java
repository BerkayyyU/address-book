package com.example.application.repositories;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ContactRepository extends CrudRepository<Contact,Long> {
    //Optional<Phone> findContactByPhoneId(Long id);
    List<Contact> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

}
