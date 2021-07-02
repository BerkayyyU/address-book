package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;

@Route("user/:userID/contacts/new-contact")
public class NewContactView extends VerticalLayout implements BeforeEnterObserver {

    String userID;
    Long contactID=0L;
    Binder<Contact> binder = new Binder<>();
    Contact contact = new Contact();

    Button btnSave = new Button("Kaydet");
    Button btnCancel = new Button("İptal");

    Div newContactDiv = new Div();

    Icon userIcon = new Icon(VaadinIcon.USER);
    Icon backIcon = new Icon(VaadinIcon.ARROW_LEFT);

    Div divFirstName = new Div();
    Div divLastName = new Div();
    Div divCompany = new Div();
    Div divMobilePhone = new Div();
    Div divHomePhone = new Div();
    Div divJobPhone = new Div();
    Div divFaxPhone = new Div();
    Div divHomeAddress = new Div();
    Div divJobAdress = new Div();
    Div divOtherAddress = new Div();
    Div divFacebook = new Div();
    Div divTwitter = new Div();

    Label lblFirstName = new Label("Ad:");
    Label lblLastName = new Label("Soyad:");
    Label lblCompany = new Label("Şirket:");
    Label lblMobilePhone = new Label("Mobil:");
    Label lblHomePhone = new Label("Ev:");
    Label lblJobPhone = new Label("İş:");
    Label lblFaxPhone = new Label("Fax:");
    Label lblHomeAddress = new Label("Ev:");
    Label lblJobAddress = new Label("İş:");
    Label lblOtherAddress = new Label("Diğer:");
    Label lblFacebook = new Label("Facebook:");
    Label lblTwitter = new Label("Twitter:");

    Label adres = new Label("Adres");
    Label telefon = new Label("Telefon");
    Label sosyalMedya = new Label("Sosyal Medya");

    TextField firstName = new TextField( );
    TextField lastName = new TextField();
    TextField company = new TextField();
    TextField mobilePhone = new TextField();
    TextField homePhone = new TextField();
    TextField jobPhone = new TextField();
    TextField faxPhone = new TextField();
    TextArea homeAdress = new TextArea();
    TextArea jobAddress = new TextArea();
    TextArea otherAddress = new TextArea();
    TextField facebook = new TextField();
    TextField twitter = new TextField();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    private final ContactService contactService;
    private final UserService userService;

    public NewContactView(ContactService contactService, UserService userService){

        this.contactService = contactService;
        this.userService = userService;

        binderBind();



        btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        newContactDiv.setClassName("contactdetails-newcontact-view");
        verticalLayout.setClassName("vertical");
        backIcon.setSize("30px");
        backIcon.getStyle().set("cursor", "pointer");
        userIcon.setClassName("user-icon");
        userIcon.setSize("150px");
        adres.setClassName("telefon-adres-sosyalmedya-header");
        adres.addClassName("adres-header");
        telefon.setClassName("telefon-adres-sosyalmedya-header");
        telefon.addClassName("telefon-header");
        sosyalMedya.setClassName("telefon-adres-sosyalmedya-header");
        sosyalMedya.addClassName("sosyalmedya-header");
        horizontalLayout.setClassName("horizontal");
        btnSave.setClassName("update-save-button");

        divAdd();

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany,lblMobilePhone,lblHomePhone,lblJobPhone,lblFaxPhone,lblHomeAddress,lblJobAddress,lblOtherAddress,lblFacebook,lblTwitter}) {
            label.setClassName("labels");
        }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany,divMobilePhone,divHomePhone,divJobPhone,divFaxPhone,divHomeAddress,divJobAdress,divOtherAddress,divFacebook,divTwitter}) {
            div.setClassName("divs");
        }
        for (TextField textField : new TextField[]{firstName,lastName,company,mobilePhone,homePhone,jobPhone,faxPhone,facebook,twitter}) {
            textField.setClassName("textfield-textarea-width");
        }
        for (TextArea textArea : new TextArea[]{homeAdress,jobAddress,otherAddress}) {
            textArea.setClassName("textfield-textarea-width");
        }

        backIcon.addClickListener(iconClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        btnCancel.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        btnSave.addClickListener(buttonClickEvent -> {
            try {
                binder.writeBean(contact);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            contact.setId(contactID);
            contactService.save(contact);

            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        horizontalLayout.add(btnCancel,btnSave);
        verticalLayout.add(backIcon,userIcon,divFirstName,divLastName,divCompany,telefon,divMobilePhone,divHomePhone,divJobPhone,divFaxPhone,adres,divHomeAddress,divJobAdress,divOtherAddress,sosyalMedya,divFacebook,divTwitter,horizontalLayout);
        newContactDiv.add(verticalLayout);
        add(newContactDiv);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();
        User user = userService.getUserById(Long.valueOf(userID));
        contact.setUser(user);

    }

    private void binderBind(){
        binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany);
        binder.bind(mobilePhone,Contact::getMobilePhone,Contact::setMobilePhone);
        binder.bind(homePhone,Contact::getHomePhone,Contact::setHomePhone);
        binder.bind(jobPhone,Contact::getJobPhone,Contact::setJobPhone);
        binder.bind(faxPhone,Contact::getFaxPhone,Contact::setFaxPhone);
        binder.bind(homeAdress,Contact::getHomeAddress,Contact::setHomeAddress);
        binder.bind(jobAddress,Contact::getJobAddress,Contact::setJobAddress);
        binder.bind(otherAddress,Contact::getOtherAddress,Contact::setOtherAddress);
        binder.bind(facebook,Contact::getFacebook,Contact::setFacebook);
        binder.bind(twitter,Contact::getTwitter,Contact::setTwitter);
    }

    private void divAdd(){
        divFirstName.add(lblFirstName,firstName);
        divLastName.add(lblLastName,lastName);
        divCompany.add(lblCompany,company);
        divMobilePhone.add(lblMobilePhone,mobilePhone);
        divHomePhone.add(lblHomePhone,homePhone);
        divJobPhone.add(lblJobPhone,jobPhone);
        divFaxPhone.add(lblFaxPhone,faxPhone);
        divHomeAddress.add(lblHomeAddress,homeAdress);
        divJobAdress.add(lblJobAddress,jobAddress);
        divOtherAddress.add(lblOtherAddress,otherAddress);
        divFacebook.add(lblFacebook,facebook);
        divTwitter.add(lblTwitter,twitter);
    }


}
