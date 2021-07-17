package com.example.application.views;

import com.example.application.models.*;
import com.example.application.services.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Set;

@Route("user/:userID/contacts/new-contact/:newContactId")
@CssImport("./styles/AdresDefteri.css")
public class NewContactView extends VerticalLayout implements BeforeEnterObserver {

    private final ContactService contactService;
    private final UserService userService;
    private final PhoneService phoneService;
    private final AddressService addressService;
    private final SocialMediaService socialMediaService;

    String userID;
    Long newID = 0L;
    Binder<Contact> binder = new Binder<>();

    Image userImg;
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);
    Div imgUpload = new Div();

    Div newContactDiv = new Div();
    Div divFirstName = new Div();
    Div divLastName = new Div();
    Div divCompany = new Div();
    Div divPhone = new Div();
    Div divAdress = new Div();
    Div divSocialMedia = new Div();

    Label lblFirstName = new Label("Ad:");
    Label lblLastName = new Label("Soyad:");
    Label lblCompany = new Label("Şirket:");
    Label adres = new Label("Adres");
    Label telefon = new Label("Telefon");
    Label sosyalMedya = new Label("Sosyal Medya");

    TextField firstName = new TextField( );
    TextField lastName = new TextField();
    TextField company = new TextField();
    TextField txtPhoneNo = new TextField();
    TextField txtAddress = new TextField();
    TextField txtSocialMediaLink = new TextField();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Grid<Address> addressGrid = new Grid<>(Address.class);
    Grid<Phone> phoneGrid = new Grid(Phone.class);
    Grid<SocialMedia> socialMediaGrid = new Grid(SocialMedia.class);

    Select<String> selectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    Select<String> selectAddressType = new Select<>("Ev","İş","Diğer");
    Select<String> selectSocialMediaType = new Select<>("Facebook","Twitter","İnstagram","Linkedin");

    Button btnDeletePhone = new Button("Sil");
    Button btnUpdatePhone = new Button("Güncelle");
    Button btnCancelPhone = new Button("İptal");
    Button btnSavePhone = new Button("Kaydet");
    Button btnDeleteAddress = new Button("Sil");
    Button btnUpdateAddress = new Button("Güncelle");
    Button btnCancelAddress = new Button("İptal");
    Button btnSaveAddress = new Button("Kaydet");
    Button btnDeleteSocialMedia = new Button("Sil");
    Button btnUpdateSocialMedia = new Button("Güncelle");
    Button btnCancelSocialMedia = new Button("İptal");
    Button btnSaveSocialMedia = new Button("Kaydet");
    Button btnSaveContact = new Button("Kaydet");
    Button btnCancelContact = new Button("İptal");

    Dialog dialog = new Dialog();

    Icon addPhone = new Icon(VaadinIcon.PLUS);
    Icon addAddress = new Icon(VaadinIcon.PLUS);
    Icon addSocialMedia = new Icon(VaadinIcon.PLUS);

    Div phoneHeaderAndAdd = new Div(telefon,addPhone);
    Div addressHeaderAndAdd = new Div(adres,addAddress);
    Div socialMediaHeaderAndAdd = new Div(sosyalMedya, addSocialMedia);

    public NewContactView(ContactService contactService, UserService userService, PhoneService phoneService, AddressService addressService, SocialMediaService socialMediaService){

        this.contactService = contactService;
        this.userService = userService;
        this.phoneService = phoneService;
        this.addressService = addressService;
        this.socialMediaService = socialMediaService;

        binderBind();

        selectPhoneType.setValue("Mobil");
        selectAddressType.setValue("Ev");
        selectSocialMediaType.setValue("Facebook");
        imgUpload.setClassName("img-upload");
        newContactDiv.setClassName("contactdetails-newcontact-view");
        verticalLayout.setClassName("vertical");
        horizontalLayout.setClassName("horizontal");
        btnSaveContact.setClassName("update-save-button");

        dialog.setModal(true);

        divAdd();

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany}) {
            label.setClassName("labels"); }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany}) {
            div.setClassName("divs"); }
        for (TextField textField : new TextField[]{firstName,lastName,company,txtPhoneNo,txtAddress,txtSocialMediaLink}) {
            textField.setClassName("textfields"); }
        for (Select select : new Select[]{selectPhoneType,selectAddressType,selectSocialMediaType }) {
            select.setClassName("textfields"); }
        for (Button button : new Button[]{btnCancelContact,btnCancelPhone,btnCancelAddress,btnCancelSocialMedia,btnDeletePhone,btnDeleteAddress,btnDeleteSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_ERROR); }
        for (Button button : new Button[]{btnSavePhone,btnSaveAddress,btnSaveSocialMedia,btnUpdatePhone,btnUpdateAddress,btnUpdateSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            button.setClassName("margin-left"); }
        btnSaveContact.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        for (Icon icon : new Icon[]{addPhone,addAddress,addSocialMedia}) {
            icon.setClassName("icon-add"); }
        for (Grid grid : new Grid[]{phoneGrid,addressGrid,socialMediaGrid}) {
            grid.setHeight("300px"); }
        for(Div div : new Div[]{phoneHeaderAndAdd,addressHeaderAndAdd,socialMediaHeaderAndAdd}){
            div.setClassName("space-between"); }
        for(Div div : new Div[]{divPhone,divAdress,divSocialMedia}){
            div.setClassName("div-phone-address-socialMedia"); }
        for(Label label : new Label[]{adres,telefon,sosyalMedya}){
            label.setClassName("telefon-adres-sosyalmedya-header"); }

        //---------------------------------------PHONE-------------------------------------------
        addPhone.addClickListener(iconClickEvent -> {
            dialog.removeAll();
            dialog.add(divPhone,btnCancelPhone,btnSavePhone);
            dialog.open();

            btnCancelPhone.addClickListener(buttonClickEvent -> {
                selectPhoneType.setValue("Mobil");
                txtPhoneNo.setValue("");
                dialog.close(); });
        });

        phoneGrid.addItemClickListener(phoneItemClickEvent -> {
            dialog.removeAll();
            dialog.add(divPhone,btnDeletePhone,btnUpdatePhone);
            dialog.open();

            Phone phone = phoneService.getPhone(phoneItemClickEvent.getItem().getId());
            Binder<Phone> phoneBinder = new Binder();
            phoneBinder.bind(txtPhoneNo,Phone::getNo,Phone::setNo);
            phoneBinder.bind(selectPhoneType,Phone::getType,Phone::setType);
            phoneBinder.readBean(phone);

            dialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
                phoneBinder.removeBinding(txtPhoneNo);
                phoneBinder.removeBinding(selectPhoneType);
                dialog.isCloseOnOutsideClick(); });

            btnDeletePhone.addClickListener(buttonClickEvent -> {
                phoneService.delete(phone);
                dialog.close();
                UI.getCurrent().getPage().reload(); });

            btnUpdatePhone.addClickListener(buttonClickEvent -> {
                phoneService.update(phone,selectPhoneType.getValue(),txtPhoneNo.getValue());
                dialog.close();
                UI.getCurrent().getPage().reload(); });
        });
        //---------------------------------------ADDRESS-------------------------------------------------
        addAddress.addClickListener(iconClickEvent -> {
            dialog.removeAll();
            dialog.add(divAdress,btnCancelAddress,btnSaveAddress);
            dialog.open();

            btnCancelAddress.addClickListener(buttonClickEvent -> {
                selectAddressType.setValue("Ev");
                txtAddress.setValue("");
                dialog.close(); });
        });

        addressGrid.addItemClickListener(addressItemClickEvent -> {
            dialog.removeAll();
            dialog.add(divAdress,btnDeleteAddress,btnUpdateAddress);
            dialog.open();

            Address address = addressService.getAddress(addressItemClickEvent.getItem().getId());
            Binder<Address> addressBinder = new Binder();
            addressBinder.bind(txtAddress,Address::getAddressText,Address::setAddressText);
            addressBinder.bind(selectAddressType,Address::getType,Address::setType);
            addressBinder.readBean(address);

            dialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
                addressBinder.removeBinding(txtAddress);
                addressBinder.removeBinding(selectAddressType);
                dialog.isCloseOnOutsideClick(); });

            btnDeleteAddress.addClickListener(buttonClickEvent -> {
                addressService.delete(address);
                dialog.close();
                UI.getCurrent().getPage().reload(); });

            btnUpdateAddress.addClickListener(buttonClickEvent -> {
                addressService.update(address,selectAddressType.getValue(),txtAddress.getValue());
                dialog.close();
                UI.getCurrent().getPage().reload(); });
        });
        //---------------------------------------------SOCIAL-MEDIA----------------------------------------------
        addSocialMedia.addClickListener(iconClickEvent -> {
            dialog.removeAll();
            dialog.add(divSocialMedia,btnCancelSocialMedia,btnSaveSocialMedia);
            dialog.open();

            btnCancelSocialMedia.addClickListener(buttonClickEvent -> {
                dialog.close();
                txtSocialMediaLink.setValue("");
                selectSocialMediaType.setValue("Facebook"); });
        });

        socialMediaGrid.addItemClickListener(addressItemClickEvent -> {
            dialog.removeAll();
            dialog.add(divSocialMedia,btnDeleteSocialMedia,btnUpdateSocialMedia);
            dialog.open();

            SocialMedia socialMedia = socialMediaService.getSocialMedia(addressItemClickEvent.getItem().getId());
            Binder<SocialMedia> socialMediaBinder = new Binder();
            socialMediaBinder.bind(txtSocialMediaLink,SocialMedia::getLink,SocialMedia::setLink);
            socialMediaBinder.bind(selectSocialMediaType,SocialMedia::getType,SocialMedia::setType);
            socialMediaBinder.readBean(socialMedia);

            dialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
                socialMediaBinder.removeBinding(txtSocialMediaLink);
                socialMediaBinder.removeBinding(selectSocialMediaType);
                dialog.isCloseOnOutsideClick(); });

            btnDeleteSocialMedia.addClickListener(buttonClickEvent -> {
                socialMediaService.delete(socialMedia);
                dialog.close();
                UI.getCurrent().getPage().reload(); });

            btnUpdateSocialMedia.addClickListener(buttonClickEvent -> {
                socialMediaService.update(socialMedia,selectSocialMediaType.getValue(),txtSocialMediaLink.getValue());
                dialog.close();
                UI.getCurrent().getPage().reload(); });
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String newContactID = beforeEnterEvent.getRouteParameters().get("newContactId").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        Contact newContact = contactService.getContactByIdAndUserId(Long.valueOf(newContactID),Long.valueOf(userID));
        Set<Phone> phoneSet = phoneService.getPhoneList(Long.valueOf(newContactID));
        Set<Address> addressSet = addressService.getAddressList(Long.valueOf(newContactID));
        Set<SocialMedia> socialMediaSet = socialMediaService.getSocialMediaList(Long.valueOf(newContactID));

        StreamResource streamResource = new StreamResource("profil.png", () -> new ByteArrayInputStream(newContact.getImage()));
        streamResource.setContentType("image/png");

        userImg = new Image(streamResource, "");
        if(newContact.getImage() == null){
            userImg = new Image("/images/user.png", "Resim Yok"); }

        userImg.setClassName("userImg");
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setMaxFiles(1);
        upload.setReceiver(buffer);

        upload.addSucceededListener(event -> {
            try {
                byte[] imageBytes = IOUtils.toByteArray(buffer.getInputStream(event.getFileName()));
                StreamResource streamResource2 = new StreamResource("vaadin-logo.png", () -> new ByteArrayInputStream(imageBytes));
                streamResource2.setContentType("image/png");
                contactService.updateImage(newContact,imageBytes);
                contactService.update(newContact, firstName.getValue(), lastName.getValue(), company.getValue());
                UI.getCurrent().getPage().reload();
            } catch (IOException e) {
                e.printStackTrace(); }
        });

        setPhoneGridColumns();
        setAddressGridColumns();
        setSocialMediaGridColumns();
        phoneGrid.setItems(phoneSet);
        addressGrid.setItems(addressSet);
        socialMediaGrid.setItems(socialMediaSet);

        binder.readBean(newContact);
        //------------------------------------PHONE-----------------------------------------
        btnSavePhone.addClickListener(buttonClickEvent -> {
            if (txtPhoneNo.getValue().equals("")){
                Notification.show("Lütfen Telefon Giriniz");
            }else {
                Binder<Phone> phoneBinder = new Binder();
                Phone phone = new Phone();
                phoneBinder.bind(txtPhoneNo, Phone::getNo, Phone::setNo);
                phoneBinder.bind(selectPhoneType, Phone::getType, Phone::setType);
                try {
                    phoneBinder.writeBean(phone);
                } catch (ValidationException e) {
                    e.printStackTrace(); }
                phone.setId(newID);
                phone.setContact(newContact);
                phoneService.save(phone);
                contactService.update(newContact, firstName.getValue(), lastName.getValue(), company.getValue());
                UI.getCurrent().getPage().reload();
                binderBind(); }
        });

        btnSaveAddress.addClickListener(buttonClickEvent -> {
            if (txtAddress.getValue().equals("")){
                Notification.show("Lütfen Adres Giriniz");
            }else {
                Binder<Address> addressBinder = new Binder();
                Address address = new Address();
                addressBinder.bind(txtAddress, Address::getAddressText, Address::setAddressText);
                addressBinder.bind(selectAddressType, Address::getType, Address::setType);
                try {
                    addressBinder.writeBean(address);
                } catch (ValidationException e) {
                    e.printStackTrace(); }

                address.setId(newID);
                address.setContact(newContact);
                addressService.save(address);
                contactService.update(newContact, firstName.getValue(), lastName.getValue(), company.getValue());
                UI.getCurrent().getPage().reload();
                binderBind(); }
        });
        //-----------------------------------SOCIAL-MEDIA------------------------------------------
        btnSaveSocialMedia.addClickListener(buttonClickEvent -> {
            if (txtSocialMediaLink.getValue().equals("")){
                Notification.show("Lütfen Link Giriniz");
            }else {
                Binder<SocialMedia> socialMediaBinder = new Binder();
                SocialMedia socialMedia = new SocialMedia();
                socialMediaBinder.bind(txtSocialMediaLink, SocialMedia::getLink, SocialMedia::setLink);
                socialMediaBinder.bind(selectSocialMediaType, SocialMedia::getType, SocialMedia::setType);
                try {
                    socialMediaBinder.writeBean(socialMedia);
                } catch (ValidationException e) {
                    e.printStackTrace(); }
                socialMedia.setId(newID);
                socialMedia.setContact(newContact);
                socialMediaService.save(socialMedia);
                contactService.update(newContact, firstName.getValue(), lastName.getValue(), company.getValue());
                UI.getCurrent().getPage().reload();
                binderBind(); }
        });
        //------------------------------------CONTACT---------------------------

        btnCancelContact.addClickListener(buttonClickEvent -> {
            phoneService.deletePhones(phoneSet);
            addressService.deleteAddresses(addressSet);
            socialMediaService.deleteSocialMedias(socialMediaSet);
            contactService.delete(newContact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts"); });

        btnSaveContact.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Lütfen ad giriniz!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Lütfen soyad giriniz!");
            }else{
                contactService.update(newContact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts"); } });

        imgUpload.add(userImg,upload);
        horizontalLayout.add(btnCancelContact,btnSaveContact);
        verticalLayout.add(imgUpload,divFirstName,divLastName,divCompany,phoneHeaderAndAdd,phoneGrid,addressHeaderAndAdd,addressGrid,socialMediaHeaderAndAdd,socialMediaGrid,horizontalLayout);
        newContactDiv.add(verticalLayout);
        add(newContactDiv);
    }

    private void binderBind(){
        binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany); }

    private void divAdd(){
        divFirstName.add(lblFirstName,firstName);
        divLastName.add(lblLastName,lastName);
        divCompany.add(lblCompany,company);
        divPhone.add(selectPhoneType,txtPhoneNo);
        divAdress.add(selectAddressType,txtAddress);
        divSocialMedia.add(selectSocialMediaType,txtSocialMediaLink); }

    private void setPhoneGridColumns(){
        phoneGrid.addColumn(Phone::getType).setHeader("");
        phoneGrid.addColumn(Phone::getNo).setHeader("");
        phoneGrid.removeColumnByKey("id");
        phoneGrid.removeColumnByKey("type");
        phoneGrid.removeColumnByKey("no");
        phoneGrid.removeColumnByKey("contact"); }

    private void setAddressGridColumns(){
        addressGrid.addColumn(Address::getType).setHeader("");
        addressGrid.addColumn(Address::getAddressText).setHeader("");
        addressGrid.removeColumnByKey("id");
        addressGrid.removeColumnByKey("type");
        addressGrid.removeColumnByKey("addressText");
        addressGrid.removeColumnByKey("contact"); }

    private void setSocialMediaGridColumns(){
        socialMediaGrid.addColumn(SocialMedia::getType).setHeader("");
        socialMediaGrid.addColumn(SocialMedia::getLink).setHeader("");
        socialMediaGrid.removeColumnByKey("id");
        socialMediaGrid.removeColumnByKey("type");
        socialMediaGrid.removeColumnByKey("link");
        socialMediaGrid.removeColumnByKey("contact"); }
}
