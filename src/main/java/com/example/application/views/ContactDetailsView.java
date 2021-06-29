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

@Route("contactdetails")
@Theme(themeFolder = "adresdefteri")
public class ContactDetailsView extends VerticalLayout implements  HasUrlParameter<String> {

    private String contactID;

    private final ContactService contactService;

    Binder<Contact> binder = new Binder<>();

    Long itemIdForEdition=0L;

    TextField txtFirstName = new TextField();
    TextField txtLastName = new TextField();
    TextField txtCompany = new TextField();

    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;

        txtFirstName.setLabel("First Name");
        txtLastName.setLabel("Last Name");
        txtCompany.setLabel("Company");


        binder.bind(txtFirstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(txtLastName,Contact::getLastName,Contact::setLastName);
        binder.bind(txtCompany,Contact::getCompany,Contact::setCompany);


    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if (parameter == null) {
            txtFirstName.setPlaceholder("Ad giriniz:");
            txtLastName.setPlaceholder("Soyad giriniz:");
            txtCompany.setPlaceholder("Şirket  giriniz:");

            Button btnSave = new Button("Save", VaadinIcon.INSERT.create());
            Button btnCancel = new Button("Cancel");

            btnSave.addClickListener(buttonClickEvent -> {

                Contact contact = new Contact();
                try {
                    binder.writeBean(contact);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                contact.setId(itemIdForEdition);
                contactService.save(contact);
                UI.getCurrent().getPage().setLocation("/");

            });

            btnCancel.addClickListener(buttonClickEvent -> {
                UI.getCurrent().getPage().setLocation("/");
            });

            add(txtFirstName, txtLastName, txtCompany, btnSave, btnCancel);

        } else {

            Button btnUpdate = new Button("Update");
            Button btnDelete = new Button("Delete");

            Optional<Contact> contact = contactService.getContactById(Long.valueOf(parameter));
            //Optional<Phone> phone = contactService.getPhoneById(Long.valueOf(parameter));

            txtFirstName.setValue(contact.get().getFirstName());
            txtLastName.setValue(contact.get().getLastName());
            txtCompany.setValue(contact.get().getCompany());


            btnUpdate.addClickListener(buttonClickEvent -> {
                String txtFirstNameValue = txtFirstName.getValue();
                String txtLastNameValue = txtLastName.getValue();
                String txtCompanyValue = txtCompany.getValue();


                contactService.update(contact.get(),txtFirstNameValue,txtLastNameValue,txtCompanyValue);


                UI.getCurrent().getPage().setLocation("/");
            });

            btnDelete.addClickListener(buttonClickEvent -> {
                contactService.delete(contact.get());
                UI.getCurrent().getPage().setLocation("/");
            });

            add(txtFirstName, txtLastName, txtCompany,btnUpdate,btnDelete);
        }

    }
}
