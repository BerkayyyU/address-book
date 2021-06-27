package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
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
    //Binder<Phone> phoneBinder= new Binder<>();

    Long itemIdForEdition=0L;

    TextField txtFirstName = new TextField();
    TextField txtLastName = new TextField();
    TextField txtCompany = new TextField();
    TextField txtMobile = new TextField();
    TextField txtHome = new TextField();
    TextField txtJob = new TextField();
    TextField txtFax = new TextField();



    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;

        txtFirstName.setLabel("First Name");
        txtLastName.setLabel("Last Name");
        txtCompany.setLabel("Company");
        txtMobile.setLabel("Mobile");
        txtHome.setLabel("Home");
        txtJob.setLabel("Job");
        txtFax.setLabel("Fax");
        txtFirstName.setClassName("first-name");

        /*binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany);
        phoneBinder.bind(mobile,Phone::getMobile,Phone::setMobile);*/

    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        if (parameter == null) {
            txtFirstName.setPlaceholder("Ad giriniz:");
            txtLastName.setPlaceholder("Soyad giriniz:");
            txtCompany.setPlaceholder("Şirket  giriniz:");
            txtMobile.setPlaceholder("mobile  giriniz:");
            txtHome.setPlaceholder("home  giriniz:");
            txtJob.setPlaceholder("job  giriniz:");
            txtFax.setPlaceholder("fax  giriniz:");

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

            add(txtFirstName, txtLastName, txtCompany, txtMobile, txtHome, txtJob, txtFax, btnSave, btnCancel);

        } else {

            Button btnUpdate = new Button("Update");
            Button btnDelete = new Button("Delete");

            Optional<Contact> contact = contactService.getContactById(Long.valueOf(parameter));
            //Optional<Phone> phone = contactService.getPhoneById(Long.valueOf(parameter));

            txtFirstName.setValue(contact.get().getFirstName());
            txtLastName.setValue(contact.get().getLastName());
            txtCompany.setValue(contact.get().getCompany());

            txtMobile.setValue(contact.get().getPhone().getMobile());
            txtHome.setValue(contact.get().getPhone().getHome());
            txtJob.setValue(contact.get().getPhone().getJob());
            txtFax.setValue(contact.get().getPhone().getFax());



            btnUpdate.addClickListener(buttonClickEvent -> {
                String txtFirstNameValue = txtFirstName.getValue();
                String txtLastNameValue = txtLastName.getValue();
                String txtCompanyValue = txtCompany.getValue();

                String txtMobileValue = txtMobile.getValue();
                String txtHomeValue = txtHome.getValue();
                String txtJobValue = txtJob.getValue();
                String txtFaxValue = txtFax.getValue();

                /*contact.get().setFirstName(firstNames);
                contact.get().setLastName(lastNames);
                contact.get().setCompany(companys);*/

                contactService.update(contact.get(),txtFirstNameValue,txtLastNameValue,txtCompanyValue);


                UI.getCurrent().getPage().setLocation("/");
            });

            btnDelete.addClickListener(buttonClickEvent -> {
                contactService.delete(contact.get());
                UI.getCurrent().getPage().setLocation("/");
            });

            add(txtFirstName, txtLastName, txtCompany, txtMobile, txtHome, txtJob, txtFax,btnUpdate,btnDelete);
        }

    }
}
