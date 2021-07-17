package com.example.application.services;

import com.example.application.models.Phone;

import java.util.Set;

public interface PhoneService {
    Set<Phone> getPhoneList(Long contactID);
    Phone getPhone(Long phoneID);
    Phone save(Phone phone);
    Phone update(Phone phone, String phoneType, String phoneNo);
    void delete(Phone phone);
    void deletePhones(Set<Phone> phoneSet);
}
