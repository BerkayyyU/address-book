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
        contact1.setHomeAddress("Ali Duru Ev");
        contact1.setJobAddress("Ali Duru İş");
        contact1.setOtherAddress("Ali Duru Ev 2");
        contact1.setFacebook("Ali Duru Facebook");
        contact1.setTwitter("Ali Duru Twitter");

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
        contact7.setHomeAddress("Hakan Öztürk Ev");
        contact7.setJobAddress("Hakan Öztürk İş");
        contact7.setOtherAddress("Hakan Öztürk Ev 2");
        contact7.setFacebook("Hakan Öztürk Facebook");
        contact7.setTwitter("Hakan Öztürk Twitter");

        contact7.setUser(user1);
        contactService.save(contact7);

    }
}
