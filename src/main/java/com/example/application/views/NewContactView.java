package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;

@Route("user/:userID/contacts/new-contact")
public class NewContactView extends VerticalLayout implements BeforeEnterObserver {

    String userID;
    Long contactID=0L;

    Binder<Contact> binder = new Binder<>();

    Contact contact = new Contact();

    Button btnSave = new Button("Save", VaadinIcon.INSERT.create());
    Button btnCancel = new Button("Cancel");

    TextField txtFirstName = new TextField("First Name", "Please enter first name");
    TextField txtLastName = new TextField("Last Name","Please enter last name");
    TextField txtCompany = new TextField("Company","Please enter company");
    TextField txtMobilePhone = new TextField("Mobile Phone","Please enter mobile phone");
    TextField txtHomePhone = new TextField("Home Phone","Please enter home phone");
    TextField txtJobPhone = new TextField("Job Phone","Please enter job phone");
    TextField txtFaxPhone = new TextField("Fax Phone","Please enter fax phone");
    TextField txtHomeAdress = new TextField("Home Address","Please enter home address");
    TextField txtJobAddress = new TextField("Job Address","Please enter job address");
    TextField txtOtherAddress = new TextField("Other Address","Please enter other address");

    private final ContactService contactService;
    private final UserService userService;

    public NewContactView(ContactService contactService, UserService userService){

        this.contactService = contactService;
        this.userService = userService;

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

        btnSave.addClickListener(buttonClickEvent -> {
            try {
                binder.writeBean(contact);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            contact.setId(contactID);
            contactService.save(contact);

            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        btnCancel.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        add(txtFirstName, txtLastName,txtCompany, txtMobilePhone,txtHomePhone,txtJobPhone,txtFaxPhone,txtHomeAdress,txtJobAddress,txtOtherAddress, btnSave, btnCancel);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        User user = userService.getUserById(Long.valueOf(userID));
        contact.setUser(user);

    }


}
