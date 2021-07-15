package com.example.application.views;

import com.example.application.models.Contact;
import com.example.application.models.Phone;
import com.example.application.models.User;
import com.example.application.services.ContactService;
import com.example.application.services.PhoneService;
import com.example.application.services.UserService;
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

@Route("user/:userID/contacts/new-contact/:newContactId")
@CssImport("./styles/AdresDefteri.css")
public class NewContactView extends VerticalLayout implements BeforeEnterObserver {

    String userID;
    //Long contactID=0L;
    Binder<Contact> binder = new Binder<>();
    //Contact contact = new Contact();

    Button btnSave = new Button("Kaydet");
    Button btnCancel = new Button("İptal");

    Div newContactDiv = new Div();

    Icon userIcon = new Icon(VaadinIcon.USER);

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

    TextField firstName = new TextField( );
    TextField lastName = new TextField();
    TextField company = new TextField();
    TextArea homeAdress = new TextArea();
    TextArea jobAddress = new TextArea();
    TextArea otherAddress = new TextArea();
    TextField facebook = new TextField();
    TextField twitter = new TextField();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    private final ContactService contactService;
    private final UserService userService;
    private final PhoneService phoneService;

    Binder<Phone> phoneBinder = new Binder();
    Grid<Phone> phoneGrid = new Grid(Phone.class);
    Phone phone = new Phone();
    Long phoneID = 0L;
    Select<String> selectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    TextField txtPhoneNo = new TextField("No girin");
    Button btnDeletePhone = new Button("Sil");
    Button btnUpdatePhone = new Button("Güncelle");
    Button btnCancelPhone = new Button("İptal");
    Button btnSavePhone = new Button("Kaydet");
    Dialog phoneDialog = new Dialog();
    Icon iconAdd = new Icon(VaadinIcon.PLUS);
    Select<String> newSelectPhoneType2 = new Select<>("Mobil","Ev","İş","Fax");
    TextField newTxtPhoneNo2 = new TextField("No girin");

    public NewContactView(ContactService contactService, UserService userService, PhoneService phoneService){

        this.contactService = contactService;
        this.userService = userService;
        this.phoneService = phoneService;

        binderBind();

        btnCancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnSave.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        newContactDiv.setClassName("contactdetails-newcontact-view");
        verticalLayout.setClassName("vertical");
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

        phoneGrid.setClassName("contacts-grid");

        phoneDialog.setModal(true);

        divAdd();

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



        iconAdd.addClickListener(iconClickEvent -> {
            phoneDialog.add(newSelectPhoneType2,newTxtPhoneNo2,btnCancelPhone,btnSavePhone);
            phoneDialog.open();
            btnCancelPhone.addClickListener(buttonClickEvent -> {
                phoneDialog.close();
            });

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

        horizontalLayout.add(btnCancel,btnSave);
        verticalLayout.add(userIcon,divFirstName,divLastName,divCompany,telefon,iconAdd,phoneGrid,adres,divHomeAddress,divJobAdress,divOtherAddress,sosyalMedya,divFacebook,divTwitter,horizontalLayout);
        newContactDiv.add(verticalLayout);
        add(newContactDiv);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String newContactID = beforeEnterEvent.getRouteParameters().get("newContactId").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        User user = userService.getUserById(Long.valueOf(userID));
        Contact newContact = contactService.getContactByIdAndUserId(Long.valueOf(newContactID),Long.valueOf(userID));
        Set<Phone> phoneList = phoneService.getPhoneList(Long.valueOf(newContactID));


        if(phoneList==null){
            setGridColumns();
        }else{
            setGridColumns();
            phoneGrid.setItems(phoneList);
        }

        binder.readBean(newContact);

        btnSavePhone.addClickListener(buttonClickEvent -> {
            Binder<Phone> phoneBinder = new Binder();
            Phone phone = new Phone();
            phoneBinder.bind(newTxtPhoneNo2,Phone::getNo,Phone::setNo);
            phoneBinder.bind(newSelectPhoneType2,Phone::getType,Phone::setType);
            try {
                phoneBinder.writeBean(phone);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            phone.setId(phoneID);
            phone.setContact(newContact);
            phoneService.save(phone);
            contactService.update(newContact,firstName.getValue(),lastName.getValue(),company.getValue(),homeAdress.getValue(),jobAddress.getValue(),otherAddress.getValue(),facebook.getValue(),twitter.getValue());
            UI.getCurrent().getPage().reload();
            binderBind();
        });


        btnCancel.addClickListener(buttonClickEvent -> {
            phoneService.deletePhones(phoneList);
            contactService.delete(newContact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        btnSave.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Lütfen ad giriniz!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Lütfen soyad giriniz!");
            }else{
                contactService.update(newContact,firstName.getValue(),lastName.getValue(),company.getValue(),homeAdress.getValue(),jobAddress.getValue(),otherAddress.getValue(),facebook.getValue(),twitter.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
            }
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
