package com.example.application.services;

import com.example.application.models.Address;
import com.example.application.models.Phone;

import java.util.Set;

public interface AddressService {
    Set<Address> getAddressList(Long contactID);
    Address getAddress(Long addressID);
    Address save(Address address);
    Address update(Address address, String addressType, String addressText);
    void delete(Address address);
    void deleteAddresses(Set<Address> addressSet);
}
