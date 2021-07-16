package com.example.application.services;

import com.example.application.models.Address;
import com.example.application.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Set<Address> getAddressList(Long contactID) {
        Set<Address> addressSet = new HashSet<>();
        addressRepository.findAddressByContactId(contactID).iterator().forEachRemaining(addressSet::add);
        return addressSet;
    }

    @Override
    public Address getAddress(Long addressID) {
        return addressRepository.findAddressById(addressID);
    }

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address, String addressType, String addressText) {
        address.setType(addressType);
        address.setAddressText(addressText);
        return addressRepository.save(address);
    }

    @Override
    public void delete(Address address) {
        addressRepository.delete(address);
    }

    @Override
    public void deleteAddresses(Set<Address> addressSet) {
        addressRepository.deleteAll(addressSet);
    }

}
