package com.example.application.services;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
import com.example.application.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
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

    @Override
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    @Override
    public Contact update(Contact contact, String firstNames, String lastNames, String companys) {
        contact.setFirstName(firstNames);
        contact.setLastName(lastNames);
        contact.setCompany(companys);
        /*phone.setMobile(mobiles);
        phone.setHome(homes);
        phone.setJob(jobs);
        phone.setFax(faxs);*/
        return contactRepository.save(contact);
    }

    /*@Override
    public Optional<Phone> getPhoneById(Long id) {
        return contactRepository.findContactByPhoneId(id);
    }*/

}
