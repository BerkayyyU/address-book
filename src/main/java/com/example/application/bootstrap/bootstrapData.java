package com.example.application.bootstrap;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrapData implements CommandLineRunner {

    private final ContactService contactService;

    public bootstrapData(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void run(String... args) throws Exception {

        Contact contact1 = new Contact();
        contact1.setFirstName("Berkay");
        contact1.setLastName("Ulguel");
        contact1.setCompany("Galaksiya");
        contactService.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("Mert");
        contact2.setLastName("Cakar");
        contact2.setCompany("SPSS");
        contactService.save(contact2);

    }
}
