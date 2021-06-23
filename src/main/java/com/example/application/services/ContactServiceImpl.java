package com.example.application.services;

import com.example.application.models.Contact;
import com.example.application.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository; // Dependency injection

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Set<Contact> getContacts() {
        Set<Contact> contacList = new HashSet<>();
        contactRepository.findAll().iterator().forEachRemaining(contacList::add);
        return contacList;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }
}
