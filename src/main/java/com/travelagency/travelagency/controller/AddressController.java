package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.domain.AddressDto;
import com.travelagency.travelagency.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/travelagency/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.GET, value = "getAddresses")
    public List<AddressDto> getAddresses() {
        return addressService.getAddresses();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAddress")
    public AddressDto getAddress(@RequestParam Long addressId) {
        return addressService.getAddress(addressId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteAddress")
    public void deleteAddress(@RequestParam Long addressId) {
        addressService.deleteAddress(addressId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateAddress")
    public AddressDto updateAddress(@RequestBody AddressDto addressDto) {
        return addressService.update(addressDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createAddress", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createAddress(@RequestBody AddressDto addressDto) {
        addressService.create(addressDto);
    }

}
