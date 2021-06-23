package com.example.application.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    public MainView(){
        Button button = new Button("Vaadin button");

        button.addClickListener(buttonClickEvent -> {
            Notification.show("Button clicked");
        });
        add(button);
    }

}
