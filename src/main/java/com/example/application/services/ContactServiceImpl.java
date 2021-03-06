package com.example.application.services;

import com.example.application.models.Contact;
import com.example.application.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService{

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Set<Contact> getContacts(String name) {
        Set<Contact> contacList = new HashSet<>();
        contactRepository.findByFirstNameContainingOrLastNameContaining(name,name).iterator().forEachRemaining(contacList::add);
        return contacList;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public  Set<Contact> getContactsByUserId(Long id) {
        Set<Contact> contactSet = new HashSet<>();
        contactRepository.findContactsByUserId(id).iterator().forEachRemaining(contactSet::add);
        return contactSet;
    }

    @Override
    public Contact getContactByIdAndUserId(Long contactID, Long userID) {
        return contactRepository.findContactByIdAndUserId(contactID,userID);
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    @Override
    public Contact update(Contact contact, String firstName, String lastName, String company) {
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setCompany(company);

        return contactRepository.save(contact);
    }

    @Override
    public Contact updateImage(Contact contact, byte[] image) {
        contact.setImage(image);
        return contactRepository.save(contact);
    }

}
