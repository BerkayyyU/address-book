package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
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
@CssImport("./styles/ContactsView.css")
public class ContactsView extends VerticalLayout implements BeforeEnterObserver {

    String userID;
    String contactID;

    TextField search = new TextField("","Ara");
    Icon iconAdd = new Icon(VaadinIcon.PLUS);

    Div mainDiv = new Div();

    HorizontalLayout horizontalLayout = new HorizontalLayout();


    Grid<Contact> grid = new Grid<>(Contact.class);

    private final ContactService contactService;
    private final UserService userService;


    public ContactsView(ContactService contactService, UserService userService){

        this.contactService= contactService;
        this.userService = userService;





        iconAdd.addClickListener(iconClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/"+ userID + "/contacts/new-contact");
        });

        grid.setClassName("gridke");
        grid.setHeight("300px");
        grid.setWidth("200px");
        search.setWidth("150px");
        mainDiv.setClassName("main");
        horizontalLayout.add(search, iconAdd);


        grid.addItemClickListener(contactItemClickEvent -> {
             contactID = String.valueOf(contactItemClickEvent.getItem().getId());
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts/" + contactID + "/contact-details");
        });

        search.addValueChangeListener(textFieldStringComponentValueChangeEvent -> {
            searchContacts(search.getValue());
        });

        mainDiv.add(horizontalLayout,grid);
        add(mainDiv);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        Set<Contact> contactList = contactService.getContactsByUserId(Long.valueOf(userID));

        if(contactList==null){
            setGridColumns();
        }else{
            setGridColumns();
            grid.setItems(contactList);
        }
    }

    private void searchContacts(String filter){
        List<Contact> contactListFiltered = new ArrayList<>();
        contactListFiltered.addAll(contactService.getContacts(filter));
        grid.setItems(contactListFiltered);
    }

    private void setGridColumns(){
        grid.addColumn(Contact::getFirstName).setHeader("").setWidth("5px").setClassNameGenerator(firstName -> "xd");
        grid.addColumn(Contact::getLastName).setHeader("").setWidth("5px");
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("user");
        grid.removeColumnByKey("firstName");
        grid.removeColumnByKey("lastName");
        grid.removeColumnByKey("company");
        grid.removeColumnByKey("mobilePhone");
        grid.removeColumnByKey("homePhone");
        grid.removeColumnByKey("jobPhone");
        grid.removeColumnByKey("faxPhone");
        grid.removeColumnByKey("homeAddress");
        grid.removeColumnByKey("jobAddress");
        grid.removeColumnByKey("otherAddress");
        grid.removeColumnByKey("facebook");
        grid.removeColumnByKey("twitter");

    }
}
