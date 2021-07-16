package com.example.application.services;

import com.example.application.models.Contact;

import java.util.Set;

public interface ContactService {
    Set<Contact> getContacts(String filter);
    Contact  save(Contact contact);
    Set<Contact> getContactsByUserId(Long id);
    Contact getContactByIdAndUserId(Long contactID, Long userID);
    void delete(Contact contact);
    Contact update(Contact contact, String firstName, String lastName, String company, String facebook, String twitter);
}
