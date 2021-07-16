package com.example.application.repositories;

import com.example.application.models.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone,Long> {
    Phone findPhoneById(Long phoneID);
    List<Phone> findPhoneByContactId(Long contactID);
}
