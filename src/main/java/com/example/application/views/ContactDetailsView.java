package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.aspectj.weaver.ast.Not;

@Route("contactdetails/:contactID")
public class ContactDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private String contactID;

    public ContactDetailsView(){

        TextField firstName = new TextField();
        firstName.setLabel("First Name");
        TextField lastName = new TextField();
        lastName.setLabel("Last Name");
        TextField company = new TextField();
        company.setLabel("Company");


        add(firstName, lastName, company);


    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        Notification.show(contactID);
    }
}
