package com.sforin.EtherscanTranscations.controller;

import com.sforin.EtherscanTranscations.model.Address;
import com.sforin.EtherscanTranscations.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     *
     * @param address
     */
    @PostMapping("/{address}")
    public void createAddress(@PathVariable String address) {
        boolean addressExisting = false;
        Address newAddr = new Address();
        newAddr.setAddress(address);
        newAddr.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newAddr.setLastUpdateAt(new Timestamp(System.currentTimeMillis()));
        List<Address> allAddress = addressService.getAllAddresses();
        for (Address a : allAddress) {
            if(a.getAddress().equals(address)) {
                addressExisting = true;
            }
        }
        if(!addressExisting) {
            addressService.addAddress(newAddr);
        }
    }
}
