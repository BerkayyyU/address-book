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


@Route("user/:userID/contacts/:contactID/contact-details")
@CssImport("./styles/AdresDefteri.css")
public class ContactDetailsView extends VerticalLayout implements  BeforeEnterObserver{

    private final ContactService contactService;
    private final PhoneService phoneService;
    private final AddressService addressService;
    private final SocialMediaService socialMediaService;

    private String contactID;
    private String userID;
    Binder<Contact> binder = new Binder<>();

    Div contactDetailsDiv = new Div();

    VerticalLayout verticalLayout = new VerticalLayout();
    HorizontalLayout horizontalLayout = new HorizontalLayout();

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

    TextField firstName = new TextField();
    TextField lastName = new TextField();
    TextField company = new TextField();

    Button btnUpdateContact = new Button("Güncelle");
    Button btnDeleteContact = new Button("Sil");

    Grid<Phone> phoneGrid = new Grid(Phone.class);
    Grid<Address> addressGrid = new Grid<>(Address.class);
    Grid<SocialMedia> socialMediaGrid = new Grid<>(SocialMedia.class);

    Long phoneID = 0L;
    Long addressID = 0L;
    Long socialMediaID = 0L;

    Select<String> selectPhoneType = new Select<>("Mobil","Ev","İş","Fax");
    TextField txtPhoneNo = new TextField();

    Select<String> selectAddressType = new Select<>("Ev","İş","Diğer");
    TextField txtAddress = new TextField();

    Select<String> selectSocialMediaType = new Select<>("Facebook","Twitter","İnstagram","Linkedin");
    TextField txtSocialMediaLink = new TextField();

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

    Image userImg;

    Dialog phoneDialog = new Dialog();

    Icon addPhone = new Icon(VaadinIcon.PLUS);
    Icon addAddress = new Icon(VaadinIcon.PLUS);
    Icon addSocialMedia = new Icon(VaadinIcon.PLUS);

    Div phoneHeaderAndAdd = new Div(telefon,addPhone);
    Div addressHeaderAndAdd = new Div(adres,addAddress);
    Div socialMediaHeaderAndAdd = new Div(sosyalMedya, addSocialMedia);
    Div imgUpload = new Div();

    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);

    public ContactDetailsView(ContactService contactService, PhoneService phoneService, AddressService addressService, SocialMediaService socialMediaService){
        this.contactService = contactService;
        this.phoneService = phoneService;
        this.addressService = addressService;
        this.socialMediaService = socialMediaService;


        binderBind();

        for (Label label : new Label[]{lblFirstName,lblLastName,lblCompany}) {
            label.setClassName("labels");
        }
        for (Div div : new Div[]{divFirstName,divLastName,divCompany}) {
            div.setClassName("divs");
        }
        for (TextField textField : new TextField[]{firstName,lastName,company,txtPhoneNo,txtSocialMediaLink }) {
            textField.setClassName("textfield-textarea-width");
        }
        for (Button button : new Button[]{btnDeleteContact,btnCancelPhone,btnCancelAddress,btnCancelSocialMedia,btnDeletePhone,btnDeleteAddress,btnDeleteSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_ERROR);
        }
        for (Button button : new Button[]{btnUpdateContact,btnSavePhone,btnSaveAddress,btnSaveSocialMedia,btnUpdatePhone,btnUpdateAddress,btnUpdateSocialMedia}) {
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        }
        for (Icon icon : new Icon[]{addPhone,addAddress,addSocialMedia}) {
            icon.setClassName("icon-add");
        }
        for (Grid grid : new Grid[]{phoneGrid,addressGrid,socialMediaGrid}) {
            grid.setHeight("300px");
        }

        phoneHeaderAndAdd.setClassName("search-add");
        addressHeaderAndAdd.setClassName("search-add");
        socialMediaHeaderAndAdd.setClassName("search-add");

        imgUpload.setClassName("img-upload");

        divPhone.setClassName("div-phone");
        divAdress.setClassName("div-phone");

        selectPhoneType.setClassName("textfield-textarea-width");

        contactDetailsDiv.setClassName("contactdetails-newcontact-view");
        verticalLayout.setClassName("vertical");

        adres.setClassName("telefon-adres-sosyalmedya-header");
        telefon.setClassName("telefon-adres-sosyalmedya-header");
        sosyalMedya.setClassName("telefon-adres-sosyalmedya-header");

        horizontalLayout.setClassName("horizontal");
        btnUpdateContact.setClassName("update-save-button");

        phoneDialog.setModal(true);

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

        //--------------------------------------------------------------------------------------



        StreamResource streamResource = new StreamResource("user.png", () -> new ByteArrayInputStream(contact.getImage()));
        streamResource.setContentType("image/png");
        userImg = new Image(streamResource, "");

        if(contact.getImage() == null){
            userImg = new Image("/images/user.png", "Resim Yok");
        }

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

        binder.readBean(contact);

        btnUpdateContact.addClickListener(buttonClickEvent -> {
            if(firstName.getValue().equals("")){
                Notification.show("Lütfen ad giriniz!");
            }else if (lastName.getValue().equals("")){
                Notification.show("Lütfen soyad giriniz!");
            }else{
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
            }
        });

        btnDeleteContact.addClickListener(buttonClickEvent -> {
            phoneService.deletePhones(phoneSet);
            addressService.deleteAddresses(addressSet);
            socialMediaService.deleteSocialMedias(socialMediaSet);
            contactService.delete(contact);
            UI.getCurrent().getPage().setLocation("user/" + userID + "/contacts");
        });

        //---------------------------------------------------------------------------------------------

        addPhone.addClickListener(iconClickEvent -> {
            phoneDialog.removeAll();
            phoneDialog.add(divPhone,btnCancelPhone,btnSavePhone);
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
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().reload();
            }
        });

        phoneGrid.addItemClickListener(phoneItemClickEvent -> {
            Binder<Phone> phoneBinder2 = new Binder();
            phoneDialog.removeAll();
            phoneDialog.add(divPhone,btnDeletePhone,btnUpdatePhone);
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
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().reload();
            }
        });
        //-------------------------------------------------------------------
        socialMediaGrid.addItemClickListener(socialMediaItemClickEvent -> {
            Binder<SocialMedia> socialMediaBinder = new Binder();
            phoneDialog.removeAll();
            phoneDialog.add(selectSocialMediaType,txtSocialMediaLink,btnDeleteSocialMedia,btnUpdateSocialMedia);
            SocialMedia socialMedia = socialMediaService.getSocialMedia(socialMediaItemClickEvent.getItem().getId());

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
        //-----------------------------------------------------------------------------------------------
        addSocialMedia.addClickListener(iconClickEvent -> {
            phoneDialog.removeAll();
            phoneDialog.add(selectSocialMediaType,txtSocialMediaLink,btnCancelSocialMedia,btnSaveSocialMedia);
            phoneDialog.open();

        });
        btnCancelSocialMedia.addClickListener(buttonClickEvent -> {
            phoneDialog.close();
        });

        btnSaveSocialMedia.addClickListener(buttonClickEvent -> {
            if (txtSocialMediaLink.getValue().equals("")){
                Notification.show("Lütfen link giriniz");
            }else{
                Binder<SocialMedia> socialMediaBinder = new Binder();
                SocialMedia socialMedia = new SocialMedia();
                socialMediaBinder.bind(txtSocialMediaLink,SocialMedia::getLink,SocialMedia::setLink);
                socialMediaBinder.bind(selectSocialMediaType,SocialMedia::getType,SocialMedia::setType);
                try {
                    socialMediaBinder.writeBean(socialMedia);
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
                socialMedia.setId(socialMediaID);
                socialMedia.setContact(contact);
                socialMediaService.save(socialMedia);
                contactService.update(contact,firstName.getValue(),lastName.getValue(),company.getValue());
                UI.getCurrent().getPage().reload();
            }
        });


        imgUpload.add(userImg,upload);
        horizontalLayout.add(btnDeleteContact,btnUpdateContact);
        verticalLayout.add(imgUpload,divFirstName,divLastName,divCompany,phoneHeaderAndAdd,phoneGrid,addressHeaderAndAdd,addressGrid,socialMediaHeaderAndAdd,socialMediaGrid,horizontalLayout);
        contactDetailsDiv.add(verticalLayout);
        add(contactDetailsDiv);

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
