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

@Route("/contacts/:userID/new-contact")
public class NewContactView extends VerticalLayout implements BeforeEnterObserver {

    String userID;

    Binder<Contact> binder = new Binder<>();

    Contact contact = new Contact();

    Long itemIdForEdition=0L;

    TextField txtFirstName = new TextField("First Name", "Ad giriniz...");
    TextField txtLastName = new TextField("Last Name","Soyad giriniz...");
    TextField txtCompany = new TextField("Company","Şirket giriniz...");

    private final ContactService contactService; //Dependency injection
    private final UserService userService;

    public NewContactView(ContactService contactService, UserService userService){
        this.contactService = contactService;
        this.userService = userService;





        //contactService.save(contact);
        //contact.setId(itemIdForEdition);
        //contact.setUser(user);

        binder.bind(txtFirstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(txtLastName,Contact::getLastName,Contact::setLastName);
        binder.bind(txtCompany,Contact::getCompany,Contact::setCompany);




        Button btnSave = new Button("Save", VaadinIcon.INSERT.create());
        Button btnCancel = new Button("Cancel");



        btnSave.addClickListener(buttonClickEvent -> {
            try {
                binder.writeBean(contact);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            contact.setId(itemIdForEdition);
            contactService.save(contact);



            /*contact.setFirstName(txtFirstName.getValue());
            contact.setLastName(txtLastName.getValue());
            contact.setCompany(txtCompany.getValue());*/

            UI.getCurrent().getPage().setLocation("/contacts/" + userID);
        });

        add(txtFirstName, txtLastName, txtCompany, btnSave, btnCancel);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        User user = userService.getUserById(Long.valueOf(userID));
        contact.setUser(user);

    }


}
