package com.example.application.views;

import com.example.application.models.Address;
import com.example.application.models.Contact;
import com.example.application.models.Phone;
import com.example.application.services.AddressService;
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
    private final AddressService addressService;

    private String contactID;
    private String userID;
    Binder<Contact> binder = new Binder<>();

    Div contactDetailsDiv = new Div();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Icon userIcon = new Icon(VaadinIcon.USER);

    Div divFirstName = new Div();
    Div divLastName = new Div();
    Div divCompany = new Div();
    Div divFacebook = new Div();
    Div divTwitter = new Div();

    Label lblFirstName = new Label("Ad:");
    Label lblLastName = new Label("Soyad:");
    Label lblCompany = new Label("Şirket:");
    Label lblFacebook = new Label("Facebook:");
    Label lblTwitter = new Label("Twitter:");

    Label adres = new Label("Adres");
    Label telefon = new Label("Telefon");
    Label sosyalMedya = new Label("Sosyal Medya");

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField company = new TextField();
    TextField facebook = new TextField();
    TextField twitter = new TextField();

    Button btnUpdateContact = new Button("Güncelle");
    Button btnDeleteContact = new Button("Sil");


    Grid<Phone> phoneGrid = new Grid(Phone.class);
    Grid<Address> addressGrid = new Grid<>(Address.class);

    Long phoneID = 0L;

    Select<String> selectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    TextField txtPhoneNo = new TextField("No girin");

    Long addressID = 0L;

    Select<String> selectAddressType = new Select<>("Ev","İş","Diğer");
    TextArea txtAddress = new TextArea("Adres girin");

    Button btnDeletePhone = new Button("Sil");
    Button btnUpdatePhone = new Button("Güncelle");
    Button btnCancelPhone = new Button("İptal");
    Button btnSavePhone = new Button("Kaydet");

    Button btnDeleteAddress = new Button("Sil");
    Button btnUpdateAddress = new Button("Güncelle");
    Button btnCancelAddress = new Button("İptal");
    Button btnSaveAddress = new Button("Kaydet");

    Dialog phoneDialog = new Dialog();
    Icon addPhone = new Icon(VaadinIcon.PLUS);
    Icon addAddress = new Icon(VaadinIcon.PLUS);

    public ContactDetailsView(ContactService contactService, PhoneService phoneService, AddressService addressService){
        this.contactService = contactService;
        this.phoneService = phoneService;
        this.addressService = addressService;

        binderBind();

        btnDeleteContact.addThemeVariants(ButtonVariant.LUMO_ERROR);
        btnUpdateContact.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany,lblFacebook,lblTwitter}) {
            label.setClassName("labels");
        }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany,divFacebook,divTwitter}) {
            div.setClassName("divs");
        }
        for (TextField textField : new TextField[]{firstName,lastName,company,facebook,twitter}) {
            textField.setClassName("textfield-textarea-width");
        }
        for (TextArea textArea : new TextArea[]{}) {
            textArea.setClassName("textfield-textarea-width");
        }

        contactDetailsDiv.setClassName("contactdetails-newcontact-view");
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
        btnUpdateContact.setClassName("update-save-button");

        phoneGrid.setClassName("contacts-grid");
        addressGrid.setClassName("contacts-grid");

        phoneDialog.setModal(true);

        divAdd();

        horizontalLayout.add(btnDeleteContact,btnUpdateContact);
        verticalLayout.add(userIcon,divFirstName,divLastName,divCompany,telefon,addPhone,phoneGrid,adres,addAddress,addressGrid,sosyalMedya,divFacebook,divTwitter,horizontalLayout);
        contactDetailsDiv.add(verticalLayout);
        add(contactDetailsDiv);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();


        Contact contact = contactService.getContactByIdAndUserId(Long.valueOf(contactID),Long.valueOf(userID));
        Set<Phone> phoneSet = phoneService.getPhoneList(Long.valueOf(contactID));
        Set<Address> addressSet = addressService.getAddressList(Long.valueOf(contactID));

        if(phoneSet==null){
            setGridColumns();
        }else{
            setGridColumns();
            phoneGrid.setItems(phoneSet);
        }
        if(addressSet==null){
            setAddressGridColumns();
        }else{
            setAddressGridColumns();
            addressGrid.setItems(addressSet);
        }

        binder.readBean(contact);

        btnUpdateContact.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Lütfen ad giriniz!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Lütfen soyad giriniz!");
            }else{
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue(),facebook.getValue(),twitter.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
            }

        });

        btnDeleteContact.addClickListener(buttonClickEvent -> {
            phoneService.deletePhones(phoneSet);
            contactService.delete(contact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });


        addPhone.addClickListener(iconClickEvent -> {
            phoneDialog.removeAll();
            phoneDialog.add(selectPhoneType,txtPhoneNo,btnCancelPhone,btnSavePhone);
            phoneDialog.open();

        });
        btnCancelPhone.addClickListener(buttonClickEvent -> {
            phoneDialog.close();
        });

        btnSavePhone.addClickListener(buttonClickEvent -> {
            if (txtPhoneNo.getValue().equals("")){
                Notification.show("Lütfen telefon numarası giriniz");
            }else{
                Binder<Phone> phoneBinder = new Binder();
                Phone phone = new Phone();
                phoneBinder.bind(txtPhoneNo,Phone::getNo,Phone::setNo);
                phoneBinder.bind(selectPhoneType,Phone::getType,Phone::setType);
                try {
                    phoneBinder.writeBean(phone);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                phone.setId(phoneID);
                phone.setContact(contact);
                phoneService.save(phone);
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue(),facebook.getValue(),twitter.getValue());
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
                UI.getCurrent().getPage().reload();
            });

            btnUpdatePhone.addClickListener(buttonClickEvent -> {
                phoneService.update(phone2,selectPhoneType.getValue(),txtPhoneNo.getValue());
                UI.getCurrent().getPage().reload();
            });

        });
        //---------------------------------------------------------
        addressGrid.addItemClickListener(addressItemClickEvent -> {
            Binder<Address> addressBinder = new Binder();
            phoneDialog.removeAll();
            phoneDialog.add(selectAddressType,txtAddress,btnDeleteAddress,btnUpdateAddress);
            Address address = addressService.getAddress(addressItemClickEvent.getItem().getId());

            addressBinder.bind(txtAddress,Address::getAddressText,Address::setAddressText);
            addressBinder.bind(selectAddressType,Address::getType,Address::setType);
            addressBinder.readBean(address);

            phoneDialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
                addressBinder.removeBinding(txtAddress);
                addressBinder.removeBinding(selectAddressType);
                phoneDialog.isCloseOnOutsideClick();
            });

            phoneDialog.open();

            btnDeleteAddress.addClickListener(buttonClickEvent -> {
                addressService.delete(address);
                phoneDialog.close();
                UI.getCurrent().getPage().reload();
            });

            btnUpdateAddress.addClickListener(buttonClickEvent -> {
                addressService.update(address,selectAddressType.getValue(),txtAddress.getValue());
                phoneDialog.close();
                UI.getCurrent().getPage().reload();
            });

        });
        //---------------------------------------------------------------------------------------
        addAddress.addClickListener(iconClickEvent -> {
            phoneDialog.removeAll();
            phoneDialog.add(selectAddressType,txtAddress,btnCancelAddress,btnSaveAddress);
            phoneDialog.open();

        });
        btnCancelAddress.addClickListener(buttonClickEvent -> {
            phoneDialog.close();
        });

        btnSaveAddress.addClickListener(buttonClickEvent -> {
            if (txtAddress.getValue().equals("")){
                Notification.show("Lütfen adres giriniz");
            }else{
                Binder<Address> addressBinder = new Binder();
                Address address = new Address();
                addressBinder.bind(txtAddress,Address::getAddressText,Address::setAddressText);
                addressBinder.bind(selectAddressType,Address::getType,Address::setType);
                try {
                    addressBinder.writeBean(address);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                address.setId(addressID);
                address.setContact(contact);
                addressService.save(address);
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue(),facebook.getValue(),twitter.getValue());
                UI.getCurrent().getPage().reload();
            }
        });
        //-------------------------------------------------------------------


    }

    private void binderBind(){
        binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany);
        binder.bind(facebook,Contact::getFacebook,Contact::setFacebook);
        binder.bind(twitter,Contact::getTwitter,Contact::setTwitter);

    }

    private void divAdd(){
        divFirstName.add(lblFirstName,firstName);
        divLastName.add(lblLastName,lastName);
        divCompany.add(lblCompany,company);
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

    private void setAddressGridColumns(){
        addressGrid.addColumn(Address::getType).setHeader("");
        addressGrid.addColumn(Address::getAddressText).setHeader("");
        addressGrid.removeColumnByKey("id");
        addressGrid.removeColumnByKey("type");
        addressGrid.removeColumnByKey("addressText");
        addressGrid.removeColumnByKey("contact");
    }

}
