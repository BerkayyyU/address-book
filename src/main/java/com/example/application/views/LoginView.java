package com.example.application.views;

import com.example.application.models.User;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.NoTheme;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;


@Route("/")
@CssImport("./styles/now.css")
@Theme(variant = Lumo.DARK)
public class LoginView extends VerticalLayout {

    private final UserService userService;

    EmailField email = new EmailField();
    PasswordField password = new PasswordField();

    Div mainDiv = new Div();

    Button btnLogin = new Button("Giriş yap");
    Image img = new Image();


    Div div = new Div();

    public LoginView(UserService userService){
        this.userService = userService;

        email.setLabel("Email giriniz:");
        password.setLabel("Şifre giriniz:");


        VerticalLayout verticalLayout = new VerticalLayout();

        mainDiv.setClassName("main");



        Anchor anchor = new Anchor("/signin","Dont have an account?");
        verticalLayout.add(email,password,btnLogin,anchor);


        /*byte[] imageBytes;
        StreamResource resource = new StreamResource("fakeImageName.jpg", () -> new ByteArrayInputStream(imageBytes));
        Image image = new Image(resource, "alternative image text");
        Image img2 = new Image("images/NewYorkBne.jpeg","wow");*/


        btnLogin.addClickListener(buttonClickEvent -> {
            User result = userService.login(email.getValue(),password.getValue());
            if(result.getId()!=null){
                UI.getCurrent().getPage().setLocation("user/"+ result.getId()+ "/contacts");
            }else{
                Notification.show("Hatalı giriş!");
            }
        });

        mainDiv.add(verticalLayout);
        add(mainDiv);

    }
}
