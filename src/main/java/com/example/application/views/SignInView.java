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

@Route("signin")
public class SignInView extends VerticalLayout {

    private final UserService userService;

    TextField txtName = new TextField();
    TextField txtEmail = new TextField();
    PasswordField password = new PasswordField();
    PasswordField password2 = new PasswordField();

    Button btnSignIn = new Button("Kayıt Ol");

    public SignInView(UserService userService){
        this.userService = userService;

        txtName.setLabel("Ad giriniz: ");
        txtEmail.setLabel("Email giriniz:");
        password.setLabel("Şifre giriniz: ");
        password2.setLabel("Şifreyi tekrardan giriniz: ");

        btnSignIn.addClickListener(buttonClickEvent -> {
            if(password.getPattern()==password2.getPattern()){
                User newUser = new User();
                newUser.setEmail(txtEmail.getValue());
                newUser.setName(txtName.getValue());
                newUser.setPassword(password.getValue());
                userService.save(newUser);
                UI.getCurrent().getPage().setLocation("/login");
            }else{
                Notification.show("Şifreler eşleşmiyor");
            }

        });

        add(txtName,txtEmail,password,password2,btnSignIn);
    }
}
