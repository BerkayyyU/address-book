package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route("user/:userID/contacts")
@CssImport("./styles/AdresDefteri.css")
public class ContactsView extends VerticalLayout implements BeforeEnterObserver {

    String userID;
    String contactID;

    Label lblUsername = new Label();
    Button btnLogOut = new Button("Çıkış Yap");

    TextField search = new TextField("","Ara");
    Icon iconAdd = new Icon(VaadinIcon.PLUS);

    Div contactsDiv = new Div();
    Div searchAndAdd = new Div();
    Div usernameAndLogOut = new Div();

    Grid<Contact> grid = new Grid<>(Contact.class);

    private final ContactService contactService;
    private final UserService userService;


    public ContactsView(ContactService contactService, UserService userService){

        this.contactService= contactService;
        this.userService = userService;

        btnLogOut.addThemeVariants(ButtonVariant.LUMO_ERROR);

        contactsDiv.setClassName("contacts-view");
        usernameAndLogOut.setClassName("username-logout");
        lblUsername.setClassName("username");
        searchAndAdd.setClassName("search-add");
        search.setClassName("search");
        iconAdd.setClassName("icon-add");
        grid.setClassName("contacts-grid");

        btnLogOut.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("/");
        });

        search.addValueChangeListener(textFieldStringComponentValueChangeEvent -> {
            searchContacts(search.getValue());
        });



        grid.addItemClickListener(contactItemClickEvent -> {
             contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts/" + contactID + "/contact-details");
        });

        searchAndAdd.add(search, iconAdd);
        usernameAndLogOut.add(lblUsername,btnLogOut);
        contactsDiv.add(usernameAndLogOut,searchAndAdd,grid);
        add(contactsDiv);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        Set<Contact> contactList = contactService.getContactsByUserId(Long.valueOf(userID));
        User user = userService.getUserById(Long.valueOf(userID));
        lblUsername.setText(user.getName());

        if(contactList==null){
            setGridColumns();
        }else{
            setGridColumns();
            grid.setItems(contactList);
        }

        iconAdd.addClickListener(iconClickEvent -> {
            Contact contactNew = new Contact();
            contactNew.setUser(user);
            contactService.save(contactNew);
            Long newContactId = contactNew.getId();
            //UI.getCurrent().getPage().setLocation("user/"+ userID + "/contacts/new-contact");
            UI.getCurrent().getPage().setLocation("user/"+ userID + "/contacts/new-contact/" + newContactId);
        });
    }

    private void searchContacts(String filter){
        List<Contact> contactListFiltered = new ArrayList<>();
        contactListFiltered.addAll(contactService.getContacts(filter));
        grid.setItems(contactListFiltered);
    }

    private void setGridColumns(){
        grid.addColumn(Contact::getFirstName).setHeader("");
        grid.addColumn(Contact::getLastName).setHeader("");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("user");
        grid.removeColumnByKey("firstName");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");

    }
}
