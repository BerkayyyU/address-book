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

import java.util.Optional;

@Route("contactdetails")
public class ContactDetailsView extends VerticalLayout implements  HasUrlParameter<String> {

    private String contactID;

    private final ContactService contactService;

    Binder<Contact> binder = new Binder<>();

    Long itemIdForEdition=0L;

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField company = new TextField();


    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;



        firstName.setLabel("First Name");
        lastName.setLabel("Last Name");
        company.setLabel("Company");

        binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany);

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if (parameter == null) {
            firstName.setPlaceholder("Ad giriniz:");
            lastName.setPlaceholder("Soyad giriniz:");
            company.setPlaceholder("Şirket  giriniz:");

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

            add(firstName, lastName, company,btnSave, btnCancel);

        } else {

            Button btnUpdate = new Button("Update");
            Button btnDelete = new Button("Delete");

            Optional<Contact> contact = contactService.getContactById(Long.valueOf(parameter));
            firstName.setValue(contact.get().getFirstName());
            lastName.setValue(contact.get().getLastName());
            company.setValue(contact.get().getCompany());



            btnUpdate.addClickListener(buttonClickEvent -> {
                String firstNames = firstName.getValue();
                String lastNames = lastName.getValue();
                String companys = company.getValue();

                /*contact.get().setFirstName(firstNames);
                contact.get().setLastName(lastNames);
                contact.get().setCompany(companys);*/

                contactService.update(contact.get(),firstNames,lastNames,companys);

                UI.getCurrent().getPage().setLocation("/");
            });

            btnDelete.addClickListener(buttonClickEvent -> {
                contactService.delete(contact.get());
                UI.getCurrent().getPage().setLocation("/");
            });

            add(firstName, lastName, company,btnUpdate,btnDelete);
        }

    }
}
