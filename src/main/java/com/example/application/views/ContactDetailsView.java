package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;

import java.util.Optional;

@Route("contactdetails")
public class ContactDetailsView extends VerticalLayout implements BeforeEnterObserver, HasUrlParameter<String> {

    private String contactID;

    private final ContactService contactService;

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField company = new TextField();

    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;



        firstName.setLabel("First Name");
        lastName.setLabel("Last Name");
        company.setLabel("Company");

        add(firstName, lastName, company);


    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        /*contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        if (contactID=="0"){

        }else{
            /*Contact contact = new Contact();
        Long.valueOf(contactID);

        }*/
        
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if (parameter == null) {
            firstName.setPlaceholder("Ad giriniz:");
            lastName.setPlaceholder("Soyad giriniz:");
            company.setPlaceholder("Şirket  giriniz:");
        } else {
            Optional<Contact> contact = contactService.getContactById(Long.valueOf(parameter));
            firstName.setValue(contact.get().getFirstName());
            lastName.setValue(contact.get().getLastName());
            company.setValue(contact.get().getCompany());
        }

    }
}
