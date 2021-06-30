package com.example.application.bootstrap;

import com.example.application.models.Contact;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class bootstrapData implements CommandLineRunner {

    private final ContactService contactService;
    private final UserService userService;

    public bootstrapData(ContactService contactService, UserService userService) {
        this.contactService = contactService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        User user1 = new User();
        user1.setEmail("berkay");
        user1.setPassword("123");
        user1.setName("brky");
        userService.save(user1);

        User user2 = new User();
        user2.setEmail("mert");
        user2.setPassword("123");
        user2.setName("mert");
        userService.save(user2);

        Contact contact1 = new Contact();
        contact1.setFirstName("berkay");
        contact1.setLastName("Ulguel");
        contact1.setCompany("Galaksiya");
        contact1.setMobilePhone("111");
        contact1.setHomePhone("222");
        contact1.setJobPhone("333");
        contact1.setFaxPhone("444");
        contact1.setHomeAddress("berkay ev");
        contact1.setJobAddress("berkay iş");
        contact1.setOtherAddress("berkay ev2");
        contact1.setFacebook("berkay facebook");
        contact1.setTwitter("berkaytwitter");

        contact1.setUser(user1);
        contactService.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("Mert");
        contact2.setLastName("Cakar");
        contact2.setCompany("SPSS");
        contact2.setMobilePhone("999");
        contact2.setHomePhone("888");
        contact2.setJobPhone("777");
        contact2.setFaxPhone("666");
        contact2.setHomeAddress("mert ev");
        contact2.setJobAddress("mert iş");
        contact2.setOtherAddress("mert ev2");
        contact2.setFacebook("mert facebook");
        contact2.setTwitter("mert twitter");

        contact2.setUser(user1);
        contactService.save(contact2);


        Contact contact3 = new Contact();
        contact3.setFirstName("Ali");
        contact3.setLastName("Duru");
        contact3.setCompany("Dunno");


        contact3.setUser(user2);
        contactService.save(contact3);


    }
}
