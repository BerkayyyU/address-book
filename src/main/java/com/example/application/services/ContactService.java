package com.example.application.services;

import com.example.application.models.Contact;

import java.util.Optional;
import java.util.Set;

public interface ContactService {
    Set<Contact> getContacts();
    Set<Contact> getContacts(String filter);
    Contact  save(Contact contact);
    Optional<Contact> getContactById(Long id);
    Set<Contact> getContactsByUserId(Long id);
    void delete(Contact contact);
    Contact update(Contact contact, String firstName, String lastName, String company);
}
