package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.theme.Theme;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Route("/contacts/:userID")
@Theme(themeFolder = "adresdefteri")
public class MainView extends VerticalLayout implements BeforeEnterObserver {

    List<Contact> contactList = new ArrayList<>();
    String userID;
    Contact contactke = new Contact();

    String contactID;
    Long itemIdForEdition=0L;
    Binder<Contact> binder = new Binder<>();


    TextField txtSearch = new TextField();

    Grid<Contact> grid = new Grid<>(Contact.class);

    private final ContactService contactService; //Dependency injection
    private final UserService userService;


    public MainView(ContactService contactService, UserService userService){

        this.contactService= contactService;
        this.userService = userService;


        Button btnNew = new Button("Add", VaadinIcon.INSERT.create());

        btnNew.addClickListener(buttonClickEvent -> {
            /*try {
                binder.writeBean(contactke);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            contactke.setId(itemIdForEdition);
            contactService.save(contactke);
            contactke.setUser(userke.get());
            contactID= String.valueOf(contactke.getId());*/
            UI.getCurrent().getPage().setLocation("/contacts/" + userID + "/new-contact");
        });

        grid.setClassName("gridke");
        //grid.setItems(contactList);




        grid.setHeight("300px");
        grid.setWidth("200px");

        //contactList.addAll(contactService.getContacts());



        grid.addItemClickListener(contactItemClickEvent -> {
            String contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("/contacts/" + userID +"/contact-details/" + contactID);
        });



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


    /*@Override
    public void setParameter(BeforeEvent beforeEvent,  @OptionalParameter String parameter) {
        Set<Contact> contact = contactService.getContactsByUserId(Long.valueOf(parameter));
        if(contact==null){
            grid.addColumn(Contact::getFirstName).setHeader("");
            grid.addColumn(Contact::getLastName).setHeader("");
            grid.removeColumnByKey("id");
            grid.removeColumnByKey("lastName");
            grid.removeColumnByKey("company");
            grid.removeColumnByKey("firstName");
            grid.removeColumnByKey("user");

        }else{
            grid.addColumn(Contact::getFirstName).setHeader("");
            grid.addColumn(Contact::getLastName).setHeader("");
            grid.removeColumnByKey("id");
            grid.removeColumnByKey("lastName");
            grid.removeColumnByKey("company");
            grid.removeColumnByKey("firstName");
            grid.removeColumnByKey("user");
            grid.setItems(contact);
        }

    }*/

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        Set<Contact> contact = contactService.getContactsByUserId(Long.valueOf(userID));

        if(contact==null){
            grid.addColumn(Contact::getFirstName).setHeader("");
            grid.addColumn(Contact::getLastName).setHeader("");
            grid.removeColumnByKey("id");
            grid.removeColumnByKey("lastName");
            grid.removeColumnByKey("company");
            grid.removeColumnByKey("firstName");
            grid.removeColumnByKey("user");

        }else{
            grid.addColumn(Contact::getFirstName).setHeader("");
            grid.addColumn(Contact::getLastName).setHeader("");
            grid.removeColumnByKey("id");
            grid.removeColumnByKey("lastName");
            grid.removeColumnByKey("company");
            grid.removeColumnByKey("firstName");
            grid.removeColumnByKey("user");
            grid.setItems(contact);
        }
    }
}
