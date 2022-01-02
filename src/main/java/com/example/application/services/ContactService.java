package com.example.application.services;

import com.example.application.models.Contact;

import java.util.Set;

public interface ContactService {
    Set<Contact> getContacts(String filter);
    Contact  save(Contact contact);
    Set<Contact> getContactsByWebsiteUserId(Long id);
    Contact getContactByIdAndWebsiteUserId(Long contactID, Long websiteUserID);
    void delete(Contact contact);
    Contact update(Contact contact, String firstName, String lastName, String company);
    Contact updateImage(Contact contact, byte[] image);
}
