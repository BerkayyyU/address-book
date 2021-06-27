package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;


import java.util.ArrayList;
import java.util.List;

@Route
@Theme(themeFolder = "adresdefteri")
public class MainView extends VerticalLayout {

    List<Contact> contactList = new ArrayList<>();

    TextField txtSearch = new TextField();

    Grid<Contact> grid = new Grid<>(Contact.class);

    private final ContactService contactService; //Dependency injection


    public MainView(ContactService contactService){

        this.contactService= contactService;

        Button btnNew = new Button("Add", VaadinIcon.INSERT.create());

        btnNew.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("contactdetails");
        });

        grid.setClassName("gridke");
        grid.setItems(contactList);

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("firstName");
        grid.removeColumnByKey("phone");


        grid.setHeight("300px");
        grid.setWidth("200px");

        contactList.addAll(contactService.getContacts());



        grid.addItemClickListener(contactItemClickEvent -> {
            String contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("contactdetails/" + contactID);
        });

        grid.addColumn(Contact::getFirstName).setHeader("");
        grid.addColumn(Contact::getLastName).setHeader("");



        txtSearch.addValueChangeListener(textFieldStringComponentValueChangeEvent -> {
            refreshData(txtSearch.getValue());
        });

        add(grid,btnNew,txtSearch);

    }

    private void refreshData(String filter){
        List<Contact> contactListFiltered = new ArrayList<>();
        contactListFiltered.addAll(contactService.getContacts(filter));
        grid.setItems(contactListFiltered);
    }






}
