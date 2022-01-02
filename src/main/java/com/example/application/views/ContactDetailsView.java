package com.example.application.views;

import com.example.application.models.Address;
import com.example.application.models.Contact;
import com.example.application.models.Phone;
import com.example.application.models.SocialMedia;
import com.example.application.services.AddressService;
import com.example.application.services.ContactService;
import com.example.application.services.PhoneService;
import com.example.application.services.SocialMediaService;
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


@Route("user/:userID/contacts/:contactID/contact-details")
@CssImport("./styles/AdresDefteri.css")
public class ContactDetailsView extends VerticalLayout implements  BeforeEnterObserver{

    private final ContactService contactService;
    private final PhoneService phoneService;
    private final AddressService addressService;
    private final SocialMediaService socialMediaService;

    String userID;
    String contactID;
    Long newID = 0L;
    Binder<Contact> binder = new Binder<>();

    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);
    Image userImg;

    Dialog dialog = new Dialog();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    Div contactDetailsDiv = new Div();
    Div imgUpload = new Div();
    Div divFirstName = new Div();
    Div divLastName = new Div();
    Div divCompany = new Div();
    Div divPhone = new Div();
    Div divAddress = new Div();
    Div divSocialMedia = new Div();

    Label lblFirstName = new Label("First Name:");
    Label lblLastName = new Label("Last Name:");
    Label lblCompany = new Label("Company:");
    Label address = new Label("Address");
    Label phone = new Label("Phone");
    Label socialProfile = new Label("Social Profile");

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField company = new TextField();
    TextField txtPhoneNo = new TextField();
    TextField txtAddress = new TextField();
    TextField txtSocialMediaLink = new TextField();

    Grid<Phone> phoneGrid = new Grid(Phone.class);
    Grid<Address> addressGrid = new Grid<>(Address.class);
    Grid<SocialMedia> socialMediaGrid = new Grid<>(SocialMedia.class);

    Select<String> selectPhoneType = new Select<>("Mobile","Home","Work","Fax");
    Select<String> selectAddressType = new Select<>("Home","Work","Other");
    Select<String> selectSocialMediaType = new Select<>("Facebook","Twitter","Instagram","Linkedin");

    Button btnDeletePhone = new Button("Delete");
    Button btnUpdatePhone = new Button("Update");
    Button btnCancelPhone = new Button("Cancel");
    Button btnSavePhone = new Button("Save");
    Button btnDeleteAddress = new Button("Delete");
    Button btnUpdateAddress = new Button("Update");
    Button btnCancelAddress = new Button("Cancel");
    Button btnSaveAddress = new Button("Save");
    Button btnDeleteSocialMedia = new Button("Delete");
    Button btnUpdateSocialMedia = new Button("Update");
    Button btnCancelSocialMedia = new Button("Cancel");
    Button btnSaveSocialMedia = new Button("Save");
    Button btnDeleteContact = new Button("Delete");
    Button btnUpdateContact = new Button("Update");

    Icon addPhone = new Icon(VaadinIcon.PLUS);
    Icon addAddress = new Icon(VaadinIcon.PLUS);
    Icon addSocialMedia = new Icon(VaadinIcon.PLUS);

    Div phoneHeaderAndAdd = new Div(phone,addPhone);
    Div addressHeaderAndAdd = new Div(address,addAddress);
    Div socialMediaHeaderAndAdd = new Div(socialProfile, addSocialMedia);

    public ContactDetailsView(ContactService contactService, PhoneService phoneService, AddressService addressService, SocialMediaService socialMediaService){
        this.contactService = contactService;
        this.phoneService = phoneService;
        this.addressService = addressService;
        this.socialMediaService = socialMediaService;

        binderBind();

        selectPhoneType.setValue("Mobile");
        selectAddressType.setValue("Home");
        selectSocialMediaType.setValue("Facebook");

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany}) {
            label.setClassName("labels"); }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany}) {
            div.setClassName("divs"); }
        for (TextField textField : new TextField[]{firstName,lastName,company,txtPhoneNo,txtAddress,txtSocialMediaLink }) {
            textField.setClassName("textfields"); }
        for (Select select : new Select[]{selectPhoneType,selectAddressType,selectSocialMediaType }) {
            select.setClassName("textfields"); }
        for (Button button : new Button[]{btnDeleteContact,btnCancelPhone,btnCancelAddress,btnCancelSocialMedia,btnDeletePhone,btnDeleteAddress,btnDeleteSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_ERROR); }
        for (Button button : new Button[]{btnSavePhone,btnSaveAddress,btnSaveSocialMedia,btnUpdatePhone,btnUpdateAddress,btnUpdateSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            button.setClassName("margin-left"); }
        btnUpdateContact.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        for (Icon icon : new Icon[]{addPhone,addAddress,addSocialMedia}) {
            icon.setClassName("icon-add"); }
        for (Grid grid : new Grid[]{phoneGrid,addressGrid,socialMediaGrid}) {
            grid.setHeight("300px"); }
        for(Div div : new Div[]{phoneHeaderAndAdd,addressHeaderAndAdd,socialMediaHeaderAndAdd}){
            div.setClassName("space-between"); }
        for(Div div : new Div[]{divPhone,divAddress,divSocialMedia}){
            div.setClassName("div-phone-address-socialMedia"); }
        for(Label label : new Label[]{address,phone,socialProfile}){
            label.setClassName("telefon-adres-sosyalmedya-header"); }


        imgUpload.setClassName("img-upload");
        contactDetailsDiv.setClassName("contactdetails-newcontact-view");
        verticalLayout.setClassName("vertical");
        horizontalLayout.setClassName("horizontal");
        btnUpdateContact.setClassName("update-save-button");

        dialog.setModal(true);

        divAdd();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        contactID = beforeEnterEvent.getRouteParameters().get("contactID").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        Contact contact = contactService.getContactByIdAndUserId(Long.valueOf(contactID),Long.valueOf(userID));
        Set<Phone> phoneSet = phoneService.getPhoneList(Long.valueOf(contactID));
        Set<Address> addressSet = addressService.getAddressList(Long.valueOf(contactID));
        Set<SocialMedia> socialMediaSet = socialMediaService.getSocialMediaList(Long.valueOf(contactID));

        StreamResource streamResource = new StreamResource("user.png", () -> new ByteArrayInputStream(contact.getImage()));
        streamResource.setContentType("image/png");

        userImg = new Image(streamResource, "");
        if(contact.getImage() == null){
            userImg = new Image("/images/user.png", "No Image"); }

        userImg.setClassName("userImg");
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setMaxFiles(1);
        upload.setReceiver(buffer);

        upload.addSucceededListener(event -> {
            try {
                byte[] imageBytes = IOUtils.toByteArray(buffer.getInputStream(event.getFileName()));
                StreamResource streamResource2 = new StreamResource("user.png", () -> new ByteArrayInputStream(imageBytes));
                streamResource2.setContentType("image/png");
                contactService.updateImage(contact,imageBytes);
                UI.getCurrent().getPage().reload();
            } catch (IOException e) {
                e.printStackTrace(); } });

        setPhoneGridColumns();
        setAddressGridColumns();
        setSocialMediaGridColumns();
        phoneGrid.setItems(phoneSet);
        addressGrid.setItems(addressSet);
        socialMediaGrid.setItems(socialMediaSet);

        binder.readBean(contact);

        btnDeleteContact.addClickListener(buttonClickEvent -> {
            phoneService.deletePhones(phoneSet);
            addressService.deleteAddresses(addressSet);
            socialMediaService.deleteSocialMedias(socialMediaSet);
            contactService.delete(contact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts"); });

        btnUpdateContact.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Please enter a first name!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Please enter a last name!");
            }else{
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts"); } });
        //---------------------------------------------PHONE----------------------------------------------------
        addPhone.addClickListener(iconClickEvent -> {
            dialog.removeAll();
            dialog.add(divPhone,btnCancelPhone,btnSavePhone);
            dialog.open(); });

        btnCancelPhone.addClickListener(buttonClickEvent -> {
            selectPhoneType.setValue("Mobile");
            txtPhoneNo.setValue("");
            dialog.close(); });

        btnSavePhone.addClickListener(buttonClickEvent -> {
            if (txtPhoneNo.getValue().equals("")){
                Notification.show("Please enter a valid phone number!");
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
                phone.setId(newID);
                phone.setContact(contact);
                phoneService.save(phone);
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().reload();
            } });

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
                UI.getCurrent().getPage().reload(); });

            btnUpdatePhone.addClickListener(buttonClickEvent -> {
                phoneService.update(phone,selectPhoneType.getValue(),txtPhoneNo.getValue());
                UI.getCurrent().getPage().reload(); });
        });
        //---------------------------------------ADDRESS------------------------------------------------------------
        addressGrid.addItemClickListener(addressItemClickEvent -> {
            dialog.removeAll();
            dialog.add(divAddress,btnDeleteAddress,btnUpdateAddress);
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

        addAddress.addClickListener(iconClickEvent -> {
            dialog.removeAll();
            dialog.add(divAddress,btnCancelAddress,btnSaveAddress);
            dialog.open(); });

        btnCancelAddress.addClickListener(buttonClickEvent -> {
            selectAddressType.setValue("Home");
            txtAddress.setValue("");
            dialog.close(); });

        btnSaveAddress.addClickListener(buttonClickEvent -> {
            if (txtAddress.getValue().equals("")){
                Notification.show("Please enter an address!");
            }else{
                Binder<Address> addressBinder = new Binder();
                Address address = new Address();
                addressBinder.bind(txtAddress,Address::getAddressText,Address::setAddressText);
                addressBinder.bind(selectAddressType,Address::getType,Address::setType);
                try {
                    addressBinder.writeBean(address);
                } catch (ValidationException e) {
                    e.printStackTrace(); }
                address.setId(newID);
                address.setContact(contact);
                addressService.save(address);
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().reload(); }
        });
        //--------------------------------------------SOCIAL-MEDİA------------------------------------
        socialMediaGrid.addItemClickListener(socialMediaItemClickEvent -> {
            dialog.removeAll();
            dialog.add(divSocialMedia,btnDeleteSocialMedia,btnUpdateSocialMedia);
            dialog.open();

            SocialMedia socialMedia = socialMediaService.getSocialMedia(socialMediaItemClickEvent.getItem().getId());
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

        addSocialMedia.addClickListener(iconClickEvent -> {
            dialog.removeAll();
            dialog.add(divSocialMedia,btnCancelSocialMedia,btnSaveSocialMedia);
            dialog.open(); });

        btnCancelSocialMedia.addClickListener(buttonClickEvent -> {
            txtSocialMediaLink.setValue("");
            selectSocialMediaType.setValue("Facebook");
            dialog.close(); });

        btnSaveSocialMedia.addClickListener(buttonClickEvent -> {
            if (txtSocialMediaLink.getValue().equals("")){
                Notification.show("Please enter a valid link!");
            }else{
                SocialMedia socialMedia = new SocialMedia();
                Binder<SocialMedia> socialMediaBinder = new Binder();
                socialMediaBinder.bind(txtSocialMediaLink,SocialMedia::getLink,SocialMedia::setLink);
                socialMediaBinder.bind(selectSocialMediaType,SocialMedia::getType,SocialMedia::setType);

                try {
                    socialMediaBinder.writeBean(socialMedia);
                } catch (ValidationException e) {
                    e.printStackTrace(); }

                socialMedia.setId(newID);
                socialMedia.setContact(contact);
                socialMediaService.save(socialMedia);
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().reload(); } });

        imgUpload.add(userImg,upload);
        horizontalLayout.add(btnDeleteContact,btnUpdateContact);
        verticalLayout.add(imgUpload,divFirstName,divLastName,divCompany,phoneHeaderAndAdd,phoneGrid,addressHeaderAndAdd,addressGrid,socialMediaHeaderAndAdd,socialMediaGrid,horizontalLayout);
        contactDetailsDiv.add(verticalLayout);
        add(contactDetailsDiv);
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
        divAddress.add(selectAddressType,txtAddress);
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
