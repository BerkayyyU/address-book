package com.example.application.services;

import com.example.application.models.Phone;
import com.example.application.repositories.PhoneRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    @Override
    public Set<Phone> getPhoneList(Long contactID) {
        Set<Phone> phoneSet = new HashSet<>();
        phoneRepository.findPhoneByContactId(contactID).iterator().forEachRemaining(phoneSet::add);
        return phoneSet;
    }

    @Override
    public Phone getPhone(Long phoneID) {
        return phoneRepository.findPhoneById(phoneID);
    }

    /*@Override
    public List<Phone> getPhoneByContactIdAndUserId(Long contactID, Long userID) {
        return phoneRepository.findPhoneByContactIdAndUserId(contactID,userID);
    }*/

    @Override
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Override
    public Phone update(Phone phone, String phoneType, String phoneNo) {
        phone.setType(phoneType);
        phone.setNo(phoneNo);
        return phoneRepository.save(phone);
    }

    @Override
    public void delete(Phone phone) {
        phoneRepository.delete(phone);
    }

    @Override
    public void deletePhones(Set<Phone> phoneSet) {
        phoneRepository.deleteAll(phoneSet);
    }
}
