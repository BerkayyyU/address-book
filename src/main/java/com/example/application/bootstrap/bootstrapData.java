package com.example.application.bootstrap;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.PhoneService;
import com.example.application.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrapData implements CommandLineRunner {

    private final ContactService contactService;
    private final UserService userService;
    private final PhoneService phoneservice;

    public bootstrapData(ContactService contactService, UserService userService, PhoneService phoneservice) {
        this.contactService = contactService;
        this.userService = userService;
        this.phoneservice = phoneservice;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User();
        user1.setEmail("123");
        user1.setPassword("123");
        user1.setName("Berkayyy");
        userService.save(user1);

        Contact contact1 = new Contact();
        contact1.setFirstName("Ali");
        contact1.setLastName("Duru");
        contact1.setCompany("Ali Duru Şirket");


        contact1.setUser(user1);
        contactService.save(contact1);

        Phone phone1 = new Phone();
        phone1.setType("Mobil");
        phone1.setNo("111 111 111");
        phone1.setContact(contact1);
        phoneservice.save(phone1);

        Phone phone2 = new Phone();
        phone2.setType("Ev");
        phone2.setNo("222 222 222");
        phone2.setContact(contact1);
        phoneservice.save(phone2);

        Phone phone3 = new Phone();
        phone3.setType("İş");
        phone3.setNo("333 333 333");
        phone3.setContact(contact1);
        phoneservice.save(phone3);

        Contact contact2 = new Contact();
        contact2.setFirstName("Aliye");
        contact2.setLastName("Duru");
        contact2.setCompany("Aliye Duru Şirket");

        contact2.setUser(user1);
        contactService.save(contact2);


        Contact contact3 = new Contact();
        contact3.setFirstName("Mehmet");
        contact3.setLastName("Yılmaz");
        contact3.setCompany("Mehmet Yılmaz Şirket");

        contact3.setUser(user1);
        contactService.save(contact3);

        Contact contact4 = new Contact();
        contact4.setFirstName("Ayşe");
        contact4.setLastName("Yıldız");
        contact4.setCompany("Ayşe Yıldız Şirket");


        contact4.setUser(user1);
        contactService.save(contact4);

        Contact contact5 = new Contact();
        contact5.setFirstName("İrem");
        contact5.setLastName("Yıldırım");
        contact5.setCompany("İrem Yıldırım");


        contact5.setUser(user1);
        contactService.save(contact5);

        Contact contact6 = new Contact();
        contact6.setFirstName("Yağmur");
        contact6.setLastName("Şahin");
        contact6.setCompany("Yağmur Şahin Şirket");


        contact6.setUser(user1);
        contactService.save(contact6);

        Contact contact7 = new Contact();
        contact7.setFirstName("Hakan");
        contact7.setLastName("Öztürk");
        contact7.setCompany("Hakan Öztürk İş");


        contact7.setUser(user1);
        contactService.save(contact7);

    }
}
