package com.example.application.services;

import com.example.application.models.Contact;

import java.util.Optional;
import java.util.Set;

public interface ContactService {
    Set<Contact> getContacts();
    Contact save(Contact contact);
    Optional<Contact> getContactById(Long id);
}
