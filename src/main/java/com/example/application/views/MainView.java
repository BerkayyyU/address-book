package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;


import java.util.ArrayList;
import java.util.List;

@Route
@Theme(themeFolder = "adresdefteri")
public class MainView extends VerticalLayout {

    List<Contact> contactList = new ArrayList<>();

    private final ContactService contactService; //Dependency injection


    public MainView(ContactService contactService){

        this.contactService= contactService;

        Button btnNew = new Button("Add", VaadinIcon.INSERT.create());

        btnNew.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("contactdetails");
        });

        Grid<Contact> grid = new Grid<>(Contact.class);
        grid.setClassName("gridke");
        grid.setItems(contactList);

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("firstName");
        grid.removeColumnByKey("phone");


        contactList.addAll(contactService.getContacts());

        grid.addItemClickListener(contactItemClickEvent -> {
            String contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("contactdetails/" + contactID);
        });

        grid.addColumn(Contact::getFirstName).setHeader("");
        grid.addColumn(Contact::getLastName).setHeader("");
        add(grid,btnNew);

    }

}
