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
import com.vaadin.flow.component.textfield.TextArea;
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

    String userID;
    Binder<Contact> binder = new Binder<>();

    Button btnSave = new Button("Kaydet");
    Button btnCancel = new Button("İptal");

    Div newContactDiv = new Div();

    Div divFirstName = new Div();
    Div divLastName = new Div();
    Div divCompany = new Div();
    Div divPhone = new Div();
    Div divAdress = new Div();

    Label lblFirstName = new Label("Ad:");
    Label lblLastName = new Label("Soyad:");
    Label lblCompany = new Label("Şirket:");


    Label adres = new Label("Adres");
    Label telefon = new Label("Telefon");
    Label sosyalMedya = new Label("Sosyal Medya");

    TextField firstName = new TextField( );
    TextField lastName = new TextField();
    TextField company = new TextField();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

    private final ContactService contactService;
    private final UserService userService;
    private final PhoneService phoneService;
    private final AddressService addressService;
    private final SocialMediaService socialMediaService;


    Grid<Address> addressGrid = new Grid<>(Address.class);
    Grid<Phone> phoneGrid = new Grid(Phone.class);
    Grid<SocialMedia> socialMediaGrid = new Grid(SocialMedia.class);

    Long phoneID = 0L;
    Long addressID = 0L;
    Long socialMediaID = 0L;

    Image userImg;

    Select<String> selectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    TextField txtPhoneNo = new TextField("No girin");

    Select<String> selectAddressType = new Select<>("Ev","İş","Diğer");
    TextArea txtAddress = new TextArea("Adres girin");

    Select<String> selectSocialMediaType = new Select<>("Facebook","Twitter","İnstagram","Linkedin");
    TextField txtSocialMediaLink = new TextField("Link girin");

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

    Dialog phoneDialog = new Dialog();
    Icon addPhone = new Icon(VaadinIcon.PLUS);
    Icon addAddress = new Icon(VaadinIcon.PLUS);
    Icon addSocialMedia = new Icon(VaadinIcon.PLUS);

    Div imgUpload = new Div();

    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);

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


        phoneHeaderAndAdd.setClassName("search-add");
        addressHeaderAndAdd.setClassName("search-add");
        socialMediaHeaderAndAdd.setClassName("search-add");

        imgUpload.setClassName("img-upload");

        divPhone.setClassName("div-phone");
        divAdress.setClassName("div-phone");

        selectPhoneType.setClassName("textfield-textarea-width");

        newContactDiv.setClassName("contactdetails-newcontact-view");
        verticalLayout.setClassName("vertical");

        adres.setClassName("telefon-adres-sosyalmedya-header");
        telefon.setClassName("telefon-adres-sosyalmedya-header");
        sosyalMedya.setClassName("telefon-adres-sosyalmedya-header");

        horizontalLayout.setClassName("horizontal");
        btnSave.setClassName("update-save-button");



        phoneDialog.setModal(true);

        divAdd();

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany}) {
            label.setClassName("labels");
        }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany}) {
            div.setClassName("divs");
        }
        for (TextField textField : new TextField[]{firstName,lastName,company,txtPhoneNo,txtSocialMediaLink}) {
            textField.setClassName("textfield-textarea-width");
        }
        for (Button button : new Button[]{btnCancel,btnCancelPhone,btnCancelAddress,btnCancelSocialMedia,btnDeletePhone,btnDeleteAddress,btnDeleteSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        }
        for (Button button : new Button[]{btnSave,btnSavePhone,btnSaveAddress,btnSaveSocialMedia,btnUpdatePhone,btnUpdateAddress,btnUpdateSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        }
        for (Icon icon : new Icon[]{addPhone,addAddress,addSocialMedia}) {
            icon.setClassName("icon-add");
        }
        for (Grid grid : new Grid[]{phoneGrid,addressGrid,socialMediaGrid}) {
            grid.setHeight("300px");
        }



        addPhone.addClickListener(iconClickEvent -> {
            phoneDialog.add(selectPhoneType,txtPhoneNo,btnCancelPhone,btnSavePhone);
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
        //----------------------------------------------------------------------------------------
        addAddress.addClickListener(iconClickEvent -> {
            phoneDialog.add(selectAddressType,txtAddress,btnCancelAddress,btnSaveAddress);
            phoneDialog.open();
            btnCancelAddress.addClickListener(buttonClickEvent -> {
                phoneDialog.close();
            });

        });

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
        //-------------------------------------------------------------------------------------------
        addSocialMedia.addClickListener(iconClickEvent -> {
            phoneDialog.add(selectSocialMediaType,txtSocialMediaLink,btnCancelSocialMedia,btnSaveSocialMedia);
            phoneDialog.open();
            btnCancelSocialMedia.addClickListener(buttonClickEvent -> {
                phoneDialog.close();
            });

        });

        socialMediaGrid.addItemClickListener(addressItemClickEvent -> {
            Binder<SocialMedia> socialMediaBinder = new Binder();
            phoneDialog.removeAll();
            phoneDialog.add(selectSocialMediaType,txtSocialMediaLink,btnDeleteSocialMedia,btnUpdateSocialMedia);
            SocialMedia socialMedia = socialMediaService.getSocialMedia(addressItemClickEvent.getItem().getId());

            socialMediaBinder.bind(txtSocialMediaLink,SocialMedia::getLink,SocialMedia::setLink);
            socialMediaBinder.bind(selectSocialMediaType,SocialMedia::getType,SocialMedia::setType);
            socialMediaBinder.readBean(socialMedia);

            phoneDialog.addDialogCloseActionListener(dialogCloseActionEvent -> {
                socialMediaBinder.removeBinding(txtSocialMediaLink);
                socialMediaBinder.removeBinding(selectSocialMediaType);
                phoneDialog.isCloseOnOutsideClick();
            });

            phoneDialog.open();

            btnDeleteSocialMedia.addClickListener(buttonClickEvent -> {
                socialMediaService.delete(socialMedia);
                phoneDialog.close();
                UI.getCurrent().getPage().reload();
            });

            btnUpdateSocialMedia.addClickListener(buttonClickEvent -> {
                socialMediaService.update(socialMedia,selectSocialMediaType.getValue(),txtSocialMediaLink.getValue());
                phoneDialog.close();
                UI.getCurrent().getPage().reload();
            });

        });
        //-------------------------------------------------------------------------



    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        String newContactID = beforeEnterEvent.getRouteParameters().get("newContactId").get();
        userID = beforeEnterEvent.getRouteParameters().get("userID").get();

        User user = userService.getUserById(Long.valueOf(userID));
        Contact newContact = contactService.getContactByIdAndUserId(Long.valueOf(newContactID),Long.valueOf(userID));
        Set<Phone> phoneSet = phoneService.getPhoneList(Long.valueOf(newContactID));
        Set<Address> addressSet = addressService.getAddressList(Long.valueOf(newContactID));
        Set<SocialMedia> socialMediaSet = socialMediaService.getSocialMediaList(Long.valueOf(newContactID));

        //----------------------------------------------

        StreamResource streamResource = new StreamResource("profil.png", () -> new ByteArrayInputStream(newContact.getImage()));
        streamResource.setContentType("image/png");
        userImg = new Image(streamResource, "");

        if(newContact.getImage() == null){
            userImg = new Image("/images/user.png", "Resim Yok");
        }

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
                UI.getCurrent().getPage().reload();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


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
        if(socialMediaSet==null){
            setSocialMediaGridColumns();
        }else{
            setSocialMediaGridColumns();
            socialMediaGrid.setItems(socialMediaSet);
        }

        binder.readBean(newContact);

        btnSavePhone.addClickListener(buttonClickEvent -> {
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
            phone.setContact(newContact);
            phoneService.save(phone);
            contactService.update(newContact,firstName.getValue(),lastName.getValue(),company.getValue());
            UI.getCurrent().getPage().reload();
            binderBind();
        });


        btnCancel.addClickListener(buttonClickEvent -> {
            phoneService.deletePhones(phoneSet);
            addressService.deleteAddresses(addressSet);
            socialMediaService.deleteSocialMedias(socialMediaSet);
            contactService.delete(newContact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });
        //-----------------------------------------------------------------------------
        btnSaveAddress.addClickListener(buttonClickEvent -> {
            if (txtAddress.getValue().equals("")){
                Notification.show("Lütfen adres giriniz");
            }else {
                Binder<Address> addressBinder = new Binder();
                Address address = new Address();
                addressBinder.bind(txtAddress, Address::getAddressText, Address::setAddressText);
                addressBinder.bind(selectAddressType, Address::getType, Address::setType);
                try {
                    addressBinder.writeBean(address);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                address.setId(addressID);
                address.setContact(newContact);
                addressService.save(address);
                contactService.update(newContact, firstName.getValue(), lastName.getValue(), company.getValue());
                UI.getCurrent().getPage().reload();
                binderBind();
            }
        });

        //-----------------------------------------------------------------------------
        btnSaveSocialMedia.addClickListener(buttonClickEvent -> {
            if (txtSocialMediaLink.getValue().equals("")){
                Notification.show("Lütfen link giriniz");
            }else {
                Binder<SocialMedia> socialMediaBinder = new Binder();
                SocialMedia socialMedia = new SocialMedia();
                socialMediaBinder.bind(txtSocialMediaLink, SocialMedia::getLink, SocialMedia::setLink);
                socialMediaBinder.bind(selectSocialMediaType, SocialMedia::getType, SocialMedia::setType);
                try {
                    socialMediaBinder.writeBean(socialMedia);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                socialMedia.setId(socialMediaID);
                socialMedia.setContact(newContact);
                socialMediaService.save(socialMedia);
                contactService.update(newContact, firstName.getValue(), lastName.getValue(), company.getValue());
                UI.getCurrent().getPage().reload();
                binderBind();
            }
        });
        //-----------------------------------------------------------

        btnSave.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Lütfen ad giriniz!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Lütfen soyad giriniz!");
            }else{
                contactService.update(newContact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
            }
        });

        imgUpload.add(userImg,upload);
        horizontalLayout.add(btnCancel,btnSave);
        verticalLayout.add(imgUpload,divFirstName,divLastName,divCompany,phoneHeaderAndAdd,phoneGrid,addressHeaderAndAdd,addressGrid,socialMediaHeaderAndAdd,socialMediaGrid,horizontalLayout);
        newContactDiv.add(verticalLayout);
        add(newContactDiv);
    }

    private void binderBind(){
        binder.bind(firstName,Contact::getFirstName,Contact::setFirstName);
        binder.bind(lastName,Contact::getLastName,Contact::setLastName);
        binder.bind(company,Contact::getCompany,Contact::setCompany);
    }

    private void divAdd(){
        divFirstName.add(lblFirstName,firstName);
        divLastName.add(lblLastName,lastName);
        divCompany.add(lblCompany,company);
        divPhone.add(selectPhoneType,txtPhoneNo);
        divAdress.add(selectAddressType,txtAddress);
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

    private void setSocialMediaGridColumns(){
        socialMediaGrid.addColumn(SocialMedia::getType).setHeader("");
        socialMediaGrid.addColumn(SocialMedia::getLink).setHeader("");
        socialMediaGrid.removeColumnByKey("id");
        socialMediaGrid.removeColumnByKey("type");
        socialMediaGrid.removeColumnByKey("link");
        socialMediaGrid.removeColumnByKey("contact");
    }


}
