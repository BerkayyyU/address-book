package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
import com.example.application.services.ContactService;
import com.example.application.services.PhoneService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;

import java.util.Set;


@Route("user/:userID/contacts/:contactID/contact-details")
@CssImport("./styles/AdresDefteri.css")
public class ContactDetailsView extends VerticalLayout implements  BeforeEnterObserver{

    private final ContactService contactService;
    private final PhoneService phoneService;

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
    Div divHomeAddress = new Div();
    Div divJobAdress = new Div();
    Div divOtherAddress = new Div();
    Div divFacebook = new Div();
    Div divTwitter = new Div();

    Label lblFirstName = new Label("Ad:");
    Label lblLastName = new Label("Soyad:");
    Label lblCompany = new Label("Şirket:");
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
    TextArea homeAdress = new TextArea();
    TextArea jobAddress = new TextArea();
    TextArea otherAddress = new TextArea();
    TextField facebook = new TextField();
    TextField twitter = new TextField();

    Button btnUpdate = new Button("Güncelle");
    Button btnDelete = new Button("Sil");


    Grid<Phone> phoneGrid = new Grid(Phone.class);

    Long phoneID = 0L;
    Select<String> selectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    TextField txtPhoneNo = new TextField("No girin");
    Select<String> newSelectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    TextField newTxtPhoneNo = new TextField("No girin");
    Button btnDeletePhone = new Button("Sil");
    Button btnUpdatePhone = new Button("Güncelle");
    Button btnCancelPhone = new Button("İptal");
    Button btnSavePhone = new Button("Kaydet");
    Dialog phoneDialog = new Dialog();
    Icon iconAdd = new Icon(VaadinIcon.PLUS);

    public ContactDetailsView(ContactService contactService, PhoneService phoneService){
        this.contactService = contactService;
        this.phoneService = phoneService;

        binderBind();

        btnDelete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnUpdate.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany,lblHomeAddress,lblJobAddress,lblOtherAddress,lblFacebook,lblTwitter}) {
            label.setClassName("labels");
        }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany,divHomeAddress,divJobAdress,divOtherAddress,divFacebook,divTwitter}) {
            div.setClassName("divs");
        }
        for (TextField textField : new TextField[]{firstName,lastName,company,facebook,twitter}) {
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

        phoneGrid.setClassName("contacts-grid");

        phoneDialog.setModal(true);

        divAdd();

        backIcon.addClickListener(iconClickEvent -> {
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });



        horizontalLayout.add(btnDelete,btnUpdate);
        verticalLayout.add(backIcon,userIcon,divFirstName,divLastName,divCompany,telefon,iconAdd,phoneGrid,adres,divHomeAddress,divJobAdress,divOtherAddress,sosyalMedya,divFacebook,divTwitter,horizontalLayout);
        contactDetailsDiv.add(verticalLayout);
        add(contactDetailsDiv);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        Contact contact = contactService.getContactByIdAndUserId(Long.valueOf(contactID),Long.valueOf(userID));
        Set<Phone> phoneList = phoneService.getPhoneList(Long.valueOf(contactID));

        if(phoneList==null){
            setGridColumns();
        }else{
            setGridColumns();
            phoneGrid.setItems(phoneList);
        }

        binder.readBean(contact);

        btnUpdate.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Lütfen ad giriniz!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Lütfen soyad giriniz!");
            }else{
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue(),homeAdress.getValue(),jobAddress.getValue(),otherAddress.getValue(),facebook.getValue(),twitter.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
            }

        });

        btnDelete.addClickListener(buttonClickEvent -> {
            contactService.delete(contact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        iconAdd.addClickListener(iconClickEvent -> {
            phoneDialog.removeAll();
            phoneDialog.add(newSelectPhoneType,newTxtPhoneNo,btnCancelPhone,btnSavePhone);
            phoneDialog.open();

        });
        btnCancelPhone.addClickListener(buttonClickEvent -> {
            phoneDialog.close();
        });

        btnSavePhone.addClickListener(buttonClickEvent -> {
            if (newTxtPhoneNo.getValue().equals("")){
                Notification.show("Lütfen telefon numarası giriniz");
            }else{
                Binder<Phone> phoneBinder = new Binder();
                Phone phone = new Phone();
                phoneBinder.bind(newTxtPhoneNo,Phone::getNo,Phone::setNo);
                phoneBinder.bind(newSelectPhoneType,Phone::getType,Phone::setType);
                try {
                    phoneBinder.writeBean(phone);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                phone.setId(phoneID);
                phone.setContact(contact);
                phoneService.save(phone);
                UI.getCurrent().getPage().reload();
            }
        });



        phoneGrid.addItemClickListener(phoneItemClickEvent -> {
            Binder<Phone> phoneBinder2 = new Binder();
            phoneDialog.removeAll();
            phoneDialog.add(selectPhoneType,txtPhoneNo,btnDeletePhone,btnUpdatePhone);
            Phone phone2 = phoneService.getPhone(phoneItemClickEvent.getItem().getId());

            phoneBinder2.bind(txtPhoneNo,Phone::getNo,Phone::setNo);
            phoneBinder2.bind(selectPhoneType,Phone::getType,Phone::setType);
            phoneBinder2.readBean(phone2);

            phoneDialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
                phoneBinder2.removeBinding(txtPhoneNo);
                phoneBinder2.removeBinding(selectPhoneType);
                phoneDialog.isCloseOnOutsideClick();
            });

            phoneDialog.open();

            btnDeletePhone.addClickListener(buttonClickEvent -> {
                phoneService.delete(phone2);
                phoneDialog.close();
                UI.getCurrent().getPage().reload();
            });

            btnUpdatePhone.addClickListener(buttonClickEvent -> {
                phoneService.update(phone2,selectPhoneType.getValue(),txtPhoneNo.getValue());
                phoneDialog.close();
                UI.getCurrent().getPage().reload();
            });

        });

    }

    private void binderBind(){
        binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany);
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
        divHomeAddress.add(lblHomeAddress,homeAdress);
        divJobAdress.add(lblJobAddress,jobAddress);
        divOtherAddress.add(lblOtherAddress,otherAddress);
        divFacebook.add(lblFacebook,facebook);
        divTwitter.add(lblTwitter,twitter);
    }

    private void setGridColumns(){
        phoneGrid.addColumn(Phone::getType).setHeader("");
        phoneGrid.addColumn(Phone::getNo).setHeader("");
        phoneGrid.removeColumnByKey("id");
        phoneGrid.removeColumnByKey("type");
        phoneGrid.removeColumnByKey("no");
        phoneGrid.removeColumnByKey("contact");
    }

}
