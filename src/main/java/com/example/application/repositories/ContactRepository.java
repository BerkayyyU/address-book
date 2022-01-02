package com.example.application.repositories;

import com.example.application.models.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact,Long> {
    List<Contact> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    List<Contact> findContactsByWebsiteUserId(Long id);
    Contact findContactByIdAndWebsiteUserId(Long contactID, Long website );

}
