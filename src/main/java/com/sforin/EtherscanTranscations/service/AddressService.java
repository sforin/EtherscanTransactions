package com.sforin.EtherscanTranscations.service;

import com.sforin.EtherscanTranscations.model.Address;
import com.sforin.EtherscanTranscations.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElse(new Address());
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public void addAddress(Address address) {
        addressRepository.save(address);
    }

    public void updateAddress(Address address) {
        addressRepository.save(address);
    }


}
