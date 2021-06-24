package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@Route("contactdetails/:contactID")
public class ContactDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private String contactID;

    private final ContactService contactService;

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField company = new TextField();

    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;

        Binder<Contact> binder = new Binder();


        firstName.setLabel("First Name");
        lastName.setLabel("Last Name");
        company.setLabel("Company");

        add(firstName, lastName, company);


    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        /*Contact contact = new Contact();
        Long.valueOf(contactID);*/
        Optional<Contact> contact = contactService.getContactById(Long.valueOf(contactID));
        firstName.setValue(contact.get().getFirstName());
        lastName.setValue(contact.get().getLastName());
        company.setValue(contact.get().getCompany());

    }
}
