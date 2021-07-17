package com.example.application.repositories;

import com.example.application.models.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Long> {
    Address findAddressById(Long addressID);
    List<Address> findAddressByContactId(Long contactID);
}
