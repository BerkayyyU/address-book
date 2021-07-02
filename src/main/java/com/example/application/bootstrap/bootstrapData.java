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
        user1.setEmail("berkayulguel@example.com");
        user1.setPassword("123");
        user1.setName("Berkayyy");
        userService.save(user1);

        Contact contact1 = new Contact();
        contact1.setFirstName("Ali");
        contact1.setLastName("Duru");
        contact1.setCompany("Ali Duru Şirket");
        contact1.setMobilePhone("111 111");
        contact1.setHomePhone("111 222");
        contact1.setJobPhone("111 333");
        contact1.setFaxPhone("111 444");
        contact1.setHomeAddress("Ali Duru Ev");
        contact1.setJobAddress("Ali Duru İş");
        contact1.setOtherAddress("Ali Duru Ev 2");
        contact1.setFacebook("Ali Duru Facebook");
        contact1.setTwitter("Ali Duru Twitter");

        contact1.setUser(user1);
        contactService.save(contact1);

        Contact contact2 = new Contact();
        contact2.setFirstName("Aliye");
        contact2.setLastName("Duru");
        contact2.setCompany("Aliye Duru Şirket");
        contact2.setMobilePhone("222 111");
        contact2.setHomePhone("222 222");
        contact2.setJobPhone("222 333");
        contact2.setFaxPhone("222 444");
        contact2.setHomeAddress("Aliye Duru Ev");
        contact2.setJobAddress("Aliye Duru İş");
        contact2.setOtherAddress("Aliye Duru Ev 2");
        contact2.setFacebook("Aliye Duru Facebook");
        contact2.setTwitter("Aliye Duru Twitter");

        contact2.setUser(user1);
        contactService.save(contact2);


        Contact contact3 = new Contact();
        contact3.setFirstName("Mehmet");
        contact3.setLastName("Yılmaz");
        contact3.setCompany("Mehmet Yılmaz Şirket");
        contact3.setMobilePhone("333 111");
        contact3.setHomePhone("333 222");
        contact3.setJobPhone("333 333");
        contact3.setFaxPhone("333 444");
        contact3.setHomeAddress("Mehmet Yılmaz Ev");
        contact3.setJobAddress("Mehmet Yılmaz İş");
        contact3.setOtherAddress("Mehmet Yılmaz Ev 2");
        contact3.setFacebook("Mehmet Yılmaz Facebook");
        contact3.setTwitter("Mehmet Yılmaz Twitter");

        contact3.setUser(user1);
        contactService.save(contact3);

        Contact contact4 = new Contact();
        contact4.setFirstName("Ayşe");
        contact4.setLastName("Yıldız");
        contact4.setCompany("Ayşe Yıldız Şirket");
        contact4.setMobilePhone("444 111");
        contact4.setHomePhone("444 222");
        contact4.setJobPhone("444 333");
        contact4.setFaxPhone("444 444");
        contact4.setHomeAddress("Ayşe Yıldız Ev");
        contact4.setJobAddress("Ayşe Yıldız İş");
        contact4.setOtherAddress("Ayşe Yıldız Ev 2");
        contact4.setFacebook("Ayşe Yıldız Facebook");
        contact4.setTwitter("Ayşe Yıldız Twitter");

        contact4.setUser(user1);
        contactService.save(contact4);

        Contact contact5 = new Contact();
        contact5.setFirstName("İrem");
        contact5.setLastName("Yıldırım");
        contact5.setCompany("İrem Yıldırım");
        contact5.setMobilePhone("555 111");
        contact5.setHomePhone("555 222");
        contact5.setJobPhone("555 333");
        contact5.setFaxPhone("555 444");
        contact5.setHomeAddress("İrem Yıldırım Ev");
        contact5.setJobAddress("İrem Yıldırım İş");
        contact5.setOtherAddress("İrem Yıldırım Ev 2");
        contact5.setFacebook("İrem Yıldırım Facebook");
        contact5.setTwitter("İrem Yıldırım Twitter");

        contact5.setUser(user1);
        contactService.save(contact5);

        Contact contact6 = new Contact();
        contact6.setFirstName("Yağmur");
        contact6.setLastName("Şahin");
        contact6.setCompany("Yağmur Şahin Şirket");
        contact6.setMobilePhone("666 111");
        contact6.setHomePhone("666 222");
        contact6.setJobPhone("666 333");
        contact6.setFaxPhone("666 444");
        contact6.setHomeAddress("Yağmur Şahin Ev");
        contact6.setJobAddress("Yağmur Şahin İş");
        contact6.setOtherAddress("Yağmur Şahin Ev 2");
        contact6.setFacebook("Yağmur Şahin Facebook");
        contact6.setTwitter("Yağmur Şahin Twitter");

        contact6.setUser(user1);
        contactService.save(contact6);

        Contact contact7 = new Contact();
        contact7.setFirstName("Hakan");
        contact7.setLastName("Öztürk");
        contact7.setCompany("Hakan Öztürk İş");
        contact7.setMobilePhone("777 111");
        contact7.setHomePhone("777 222");
        contact7.setJobPhone("777 333");
        contact7.setFaxPhone("777 444");
        contact7.setHomeAddress("Hakan Öztürk Ev");
        contact7.setJobAddress("Hakan Öztürk İş");
        contact7.setOtherAddress("Hakan Öztürk Ev 2");
        contact7.setFacebook("Hakan Öztürk Facebook");
        contact7.setTwitter("Hakan Öztürk Twitter");

        contact7.setUser(user1);
        contactService.save(contact7);

    }
}
