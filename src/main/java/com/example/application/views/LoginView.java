package com.example.application.views;

import com.example.application.models.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("/")
@CssImport("./styles/AdresDefteri.css")
@Theme(variant = Lumo.DARK)
public class LoginView extends VerticalLayout {

    private final UserService userService;

    Div loginDiv = new Div();
    Label adresDefteri = new Label("Adres Defteri");
    VerticalLayout verticalLayout = new VerticalLayout();
    EmailField email = new EmailField("Email giriniz:");
    PasswordField password = new PasswordField("Şifre giriniz:");
    Button btnLogin = new Button("Giriş yap");
    Anchor anchor = new Anchor("/signin","Hesabınız yok mu?");
    //Image img = new Image();

    public LoginView(UserService userService){
        this.userService = userService;

        btnLogin.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        loginDiv.setClassName("signin-login-view");
        adresDefteri.setClassName("adres-defteri-header");
        email.setClassName("user-info-textfields");
        password.setClassName("user-info-textfields");
        btnLogin.setClassName("login-signin");
        anchor.setClassName("anchor");
        //img.setSrc("images/user.png");

        btnLogin.addClickListener(buttonClickEvent -> {
            User user = userService.login(email.getValue(),password.getValue());
            if(user.getId()!=null){
                UI.getCurrent().getPage().setLocation("user/"+ user.getId()+ "/contacts");
            }else{
                Notification.show("Hatalı giriş!");
            }
        });

        verticalLayout.add(email,password,btnLogin,anchor);
        loginDiv.add(verticalLayout);
        add(adresDefteri,loginDiv);
    }
}
