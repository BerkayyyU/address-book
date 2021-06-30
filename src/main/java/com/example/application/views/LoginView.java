package com.example.application.views;

import com.example.application.models.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("/")
public class LoginView extends VerticalLayout {

    private final UserService userService;

    TextField txtEmail = new TextField();

    PasswordField password = new PasswordField();

    Button btnLogin = new Button("Giriş yap");

    public LoginView(UserService userService){
        this.userService = userService;

        txtEmail.setLabel("Email giriniz:");
        password.setLabel("Şifre giriniz:");



        btnLogin.addClickListener(buttonClickEvent -> {
            User result = userService.login(txtEmail.getValue(),password.getValue());
            if(result.getId()!=null){
                UI.getCurrent().getPage().setLocation("user/"+ result.getId()+ "/contacts");
            }else{
                Notification.show("Hatalı giriş!");
            }
        });

        add(txtEmail,password,btnLogin);

    }
}
