package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.Theme;

import java.util.Optional;

@Route("/contacts/:userID/contact-details/:contactID")
@Theme(themeFolder = "adresdefteri")
public class ContactDetailsView extends VerticalLayout implements  BeforeEnterObserver{

    private String contactID;
    private String userID;

    private final ContactService contactService;

    Binder<Contact> binder = new Binder<>();

    Long itemIdForEdition=0L;

    TextField txtFirstName = new TextField("First Name");
    TextField txtLastName = new TextField("Last Name");
    TextField txtCompany = new TextField("Company");

    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;


        binder.bind(txtFirstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(txtLastName,Contact::getLastName,Contact::setLastName);
        binder.bind(txtCompany,Contact::getCompany,Contact::setCompany);


    }



    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");

        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        //Optional<Contact> contact = contactService.getContactById(Long.valueOf(contactID));
        Optional<Contact> contact = contactService.getContactByUserIdAndContactId(Long.valueOf(contactID),Long.valueOf(userID));

        txtFirstName.setValue(contact.get().getFirstName());
        txtLastName.setValue(contact.get().getLastName());
        txtCompany.setValue(contact.get().getCompany());


        btnUpdate.addClickListener(buttonClickEvent -> {
            String txtFirstNameValue = txtFirstName.getValue();
            String txtLastNameValue = txtLastName.getValue();
            String txtCompanyValue = txtCompany.getValue();


            contactService.update(contact.get(),txtFirstNameValue,txtLastNameValue,txtCompanyValue);


            UI.getCurrent().getPage().setLocation("/contacts/" + userID);
        });

        btnDelete.addClickListener(buttonClickEvent -> {
            contactService.delete(contact.get());
            UI.getCurrent().getPage().setLocation("/contacts/" + userID);
        });

        add(txtFirstName, txtLastName, txtCompany,btnUpdate,btnDelete);


    }
}
