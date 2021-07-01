package com.example.application.views;

import com.example.application.models.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("signin")
public class SignInView extends VerticalLayout {

    private final UserService userService;

    TextField username = new TextField();
    EmailField email = new EmailField();
    PasswordField password = new PasswordField();
    PasswordField passwordCheck = new PasswordField();

    Button btnSignIn = new Button("Kayıt Ol");

    public SignInView(UserService userService){
        this.userService = userService;

        username.setLabel("Ad giriniz: ");
        email.setLabel("Email giriniz:");
        password.setLabel("Şifre giriniz: ");
        passwordCheck.setLabel("Şifreyi tekrardan giriniz: ");

        btnSignIn.addClickListener(buttonClickEvent -> {
            if(password.getPattern()==passwordCheck.getPattern()){
                User newUser = new User();
                newUser.setEmail(email.getValue());
                newUser.setName(username.getValue());
                newUser.setPassword(password.getValue());
                userService.save(newUser);
                UI.getCurrent().getPage().setLocation("/");
            }else{
                Notification.show("Şifreler eşleşmiyor");
            }

        });

        add(username,email,password,passwordCheck,btnSignIn);
    }
}
