package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.aspectj.weaver.ast.Not;

import java.util.ArrayList;
import java.util.List;

@Route
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
        grid.setItems(contactList);

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");

        /*contactList.add(new Contact(1L,"Berkay","Ulguel","Company"));
        contactList.add(new Contact(1L,"Ali","Ulguel","Company"));
        contactList.add(new Contact(1L,"Mehmet","Ulguel","Company"));
        contactList.add(new Contact(1L,"Bilmemne","Ulguel","Company"));*/
        contactList.addAll(contactService.getContacts());

        grid.addItemClickListener(contactItemClickEvent -> {
            String contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("contactdetails/" + contactID);

        });

        grid.setColumns("firstName");
        add(grid,btnNew);
    }

}
