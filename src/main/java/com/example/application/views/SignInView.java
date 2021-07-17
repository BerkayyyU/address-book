package com.example.application.views;

import com.example.application.models.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("signin")
@CssImport("./styles/AdresDefteri.css")
public class SignInView extends VerticalLayout {

    private final UserService userService;

    Div signInDiv = new Div();
    Label adresDefteri = new Label("Adres Defteri");
    VerticalLayout verticalLayout = new VerticalLayout();
    TextField username = new TextField("Kullanıcı adı:");
    EmailField email = new EmailField("Email:");
    PasswordField password = new PasswordField("Şifre:");
    Button btnSignIn = new Button("Kayıt Ol");

    public SignInView(UserService userService){
        this.userService = userService;

        List<User> userList = userService.getUsers();

        btnSignIn.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        signInDiv.setClassName("signin-login-view");
        adresDefteri.setClassName("adres-defteri-header");
        username.setClassName("user-info-textfields");
        email.setClassName("user-info-textfields");
        password.setClassName("user-info-textfields");
        btnSignIn.setClassName("login-signin");

        btnSignIn.addClickListener(buttonClickEvent -> {
            userList.forEach(user -> {
                if(username.getValue().equals(user.getName())){
                    Notification.show("Kullanıcı Adı Başka Bir Kullanıcı Tarafından kullanılmaktadır!");
                }else if(email.getValue().equals(user.getEmail())){
                    Notification.show("Email Başka Bir Kullanıcı Tarafından Kullanılmaktadır!");
                }else{
                    if(username.getValue().equals("")){
                        Notification.show("Lütfen Kullanıcı Adı Giriniz!");
                    }else if(email.getValue().equals("")){
                        Notification.show("Lütfen Email Giriniz!");
                    }else if(password.getValue().equals("")){
                        Notification.show("Lütfen Şifre Giriniz!");
                    }else{
                        User newUser = new User();
                        newUser.setEmail(email.getValue());
                        newUser.setName(username.getValue());
                        newUser.setPassword(password.getValue());
                        userService.save(newUser);
                        UI.getCurrent().getPage().setLocation("/");
                    }
                }
            });
        });

        verticalLayout.add(username,email,password,btnSignIn);
        signInDiv.add(verticalLayout);
        add(adresDefteri,signInDiv);
    }

    private void checkUserNameAndEmail(){

    }
}
