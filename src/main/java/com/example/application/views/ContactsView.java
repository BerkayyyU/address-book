package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.Theme;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route("user/:userID/contacts")
@Theme(themeFolder = "adresdefteri")
public class ContactsView extends VerticalLayout implements BeforeEnterObserver {

    String userID;
    String contactID;

    TextField txtSearch = new TextField();

    Grid<Contact> grid = new Grid<>(Contact.class);

    private final ContactService contactService;
    private final UserService userService;


    public ContactsView(ContactService contactService, UserService userService){

        this.contactService= contactService;
        this.userService = userService;

        Button btnNew = new Button("Add", VaadinIcon.INSERT.create());

        btnNew.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/"+ userID + "/contacts/new-contact");
        });

        grid.setClassName("gridke");
        grid.setHeight("300px");
        grid.setWidth("200px");

        grid.addItemClickListener(contactItemClickEvent -> {
             contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts/" + contactID + "/contact-details");
        });

        txtSearch.addValueChangeListener(textFieldStringComponentValueChangeEvent -> {
            searchContacts(txtSearch.getValue());
        });

        add(grid,btnNew,txtSearch);

    }

    private void searchContacts(String filter){
        List<Contact> contactListFiltered = new ArrayList<>();
        contactListFiltered.addAll(contactService.getContacts(filter));
        grid.setItems(contactListFiltered);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        Set<Contact> contactList = contactService.getContactsByUserId(Long.valueOf(userID));

        if(contactList==null){
            setGridColumns();
        }else{
            setGridColumns();
            grid.setItems(contactList);
        }
    }

    public void setGridColumns(){
        grid.addColumn(Contact::getFirstName).setHeader("");
        grid.addColumn(Contact::getLastName).setHeader("");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("user");
        grid.removeColumnByKey("firstName");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("mobilePhone");
        grid.removeColumnByKey("homePhone");
        grid.removeColumnByKey("jobPhone");
        grid.removeColumnByKey("faxPhone");
        grid.removeColumnByKey("homeAddress");
        grid.removeColumnByKey("jobAddress");
        grid.removeColumnByKey("otherAddress");


    }
}
