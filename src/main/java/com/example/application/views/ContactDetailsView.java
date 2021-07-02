package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.services.ContactService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;


@Route("user/:userID/contacts/:contactID/contact-details")
@CssImport("./styles/ContactDetailsView.css")
public class ContactDetailsView extends VerticalLayout implements  BeforeEnterObserver{

    private final ContactService contactService;

    private String contactID;
    private String userID;
    Binder<Contact> binder = new Binder<>();

    Div contactDetailsDiv = new Div();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

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

    TextField firstName = new TextField();
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

    Button btnUpdate = new Button("Güncelle");
    Button btnDelete = new Button("Sil");

    public ContactDetailsView(ContactService contactService){
        this.contactService = contactService;

        binderBind();

        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnUpdate.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

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

        contactDetailsDiv.setClassName("contactdetails-newcontact-view");
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
        btnUpdate.setClassName("update-save-button");

        divAdd();

        backIcon.addClickListener(iconClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        horizontalLayout.add(btnDelete,btnUpdate);
        verticalLayout.add(backIcon,userIcon,divFirstName,divLastName,divCompany,telefon,divMobilePhone,divHomePhone,divJobPhone,divFaxPhone,adres,divHomeAddress,divJobAdress,divOtherAddress,sosyalMedya,divFacebook,divTwitter,horizontalLayout);
        contactDetailsDiv.add(verticalLayout);
        add(contactDetailsDiv);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        Contact contact = contactService.getContactByIdAndUserId(Long.valueOf(contactID),Long.valueOf(userID));

        binder.readBean(contact);

        btnUpdate.addClickListener(buttonClickEvent -> {
            contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue(),mobilePhone.getValue(),homePhone.getValue(),jobPhone.getValue(),faxPhone.getValue(),homeAdress.getValue(),jobAddress.getValue(),otherAddress.getValue(),facebook.getValue(),twitter.getValue());
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        btnDelete.addClickListener(buttonClickEvent -> {
            contactService.delete(contact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

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
