package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.Theme;



@Route("user/:userID/contacts/:contactID/contact-details")
@Theme(themeFolder = "adresdefteri")
public class ContactDetailsView extends VerticalLayout implements  BeforeEnterObserver{

    private final ContactService contactService;

    private String contactID;
    private String userID;

    Binder<Contact> binder = new Binder<>();

    Button btnUpdate = new Button("Update");
    Button btnDelete = new Button("Delete");

    TextField txtFirstName = new TextField("First Name");
    TextField txtLastName = new TextField("Last Name");
    TextField txtCompany = new TextField("Company");
    TextField txtMobilePhone = new TextField("Mobile Phone");
    TextField txtHomePhone = new TextField("Home Phone");
    TextField txtJobPhone = new TextField("Job Phone");
    TextField txtFaxPhone = new TextField("Fax Phone");
    TextField txtHomeAdress = new TextField("Home Address");
    TextField txtJobAddress = new TextField("Job Address");
    TextField txtOtherAddress = new TextField("Other Address");


    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;

        binder.bind(txtFirstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(txtLastName,Contact::getLastName,Contact::setLastName);
        binder.bind(txtCompany,Contact::getCompany,Contact::setCompany);
        binder.bind(txtMobilePhone,Contact::getMobilePhone,Contact::setMobilePhone);
        binder.bind(txtHomePhone,Contact::getHomePhone,Contact::setHomePhone);
        binder.bind(txtJobPhone,Contact::getJobPhone,Contact::setJobPhone);
        binder.bind(txtFaxPhone,Contact::getFaxPhone,Contact::setFaxPhone);
        binder.bind(txtHomeAdress,Contact::getHomeAddress,Contact::setHomeAddress);
        binder.bind(txtJobAddress,Contact::getJobAddress,Contact::setJobAddress);
        binder.bind(txtOtherAddress,Contact::getOtherAddress,Contact::setOtherAddress);


        add(txtFirstName, txtLastName, txtCompany,txtMobilePhone,txtHomePhone,txtJobPhone,txtFaxPhone,txtHomeAdress,txtJobAddress,txtOtherAddress,btnUpdate,btnDelete);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        Contact contact = contactService.getContactByIdAndUserId(Long.valueOf(contactID),Long.valueOf(userID));

        binder.readBean(contact);

        btnUpdate.addClickListener(buttonClickEvent -> {
            contactService.update(contact,txtFirstName.getValue(),txtLastName.getValue(),txtCompany.getValue(),txtMobilePhone.getValue(),txtHomePhone.getValue(),txtJobPhone.getValue(),txtFaxPhone.getValue(),txtHomeAdress.getValue(),txtJobAddress.getValue(),txtOtherAddress.getValue());
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        btnDelete.addClickListener(buttonClickEvent -> {
            contactService.delete(contact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

    }


}
