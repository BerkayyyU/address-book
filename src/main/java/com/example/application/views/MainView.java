package com.example.application.views;

import com.example.application.models.Contact;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route
public class MainView extends VerticalLayout {
    List<Contact> contactList = new ArrayList<>();
    public MainView(){
        Grid<Contact> grid = new Grid<>(Contact.class);
        grid.setItems(contactList);

        grid.removeColumnByKey("id");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");

        contactList.add(new Contact(1L,"Berkay","Ulguel","Company"));
        contactList.add(new Contact(1L,"Ali","Ulguel","Company"));
        contactList.add(new Contact(1L,"Mehmet","Ulguel","Company"));
        contactList.add(new Contact(1L,"Bilmemne","Ulguel","Company"));

        grid.setColumns("firstName");
        add(grid);
    }

}
