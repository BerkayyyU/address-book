package com.example.application.bootstrap;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
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
        contact1.setFirstName("berkay");
        contact1.setLastName("Ulguel");
        contact1.setCompany("Galaksiya");

        Phone phone1 = new Phone();
        phone1.setMobile("0534 625 10 75");
        phone1.setHome("123 123");
        phone1.setJob("222 222");
        phone1.setFax("333 333");

        phone1.setContact(contact1);
        contact1.setPhone(phone1);

        contactService.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("Mert");
        contact2.setLastName("Cakar");
        contact2.setCompany("SPSS");

        Phone phone2 = new Phone();
        phone2.setMobile("0522 222 222");
        phone2.setHome("111 1111");
        phone2.setJob("555 555");
        phone2.setFax("999 999");

        phone2.setContact(contact2);
        contact2.setPhone(phone2);
        contactService.save(contact2);

    }
}
