package com.travelagency.travelagency.service;


import com.travelagency.travelagency.domain.Address;
import com.travelagency.travelagency.domain.AddressDto;
import com.travelagency.travelagency.domain.Customer;
import com.travelagency.travelagency.domain.EntityNotFoundException;
import com.travelagency.travelagency.mapper.AddressMapper;
import com.travelagency.travelagency.repository.AddressRepository;
import com.travelagency.travelagency.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public AddressDto create(AddressDto addressDto) {
        Customer customer = customerRepository.findById(addressDto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException(Customer.class, addressDto.getCustomerId()));
        addressDto.setId(null);
        Address address = addressMapper.map(addressDto, customer);
        return addressMapper.mapToDto(addressRepository.save(address));
    }

    public AddressDto update(AddressDto addressDto) {
        addressRepository.findById(addressDto.getId()).orElseThrow(() -> new EntityNotFoundException(Address.class, addressDto.getId()));
        Customer customer = customerRepository.findById(addressDto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException(Customer.class, addressDto.getCustomerId()));
        return addressMapper.mapToDto(addressRepository.save(addressMapper.map(addressDto, customer)));
    }

    public List<AddressDto> getAddresses() {
        return addressMapper.mapToDtoList(addressRepository.findAll());
    }

    public AddressDto getAddress(final Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return addressMapper.mapToDto(address.orElseThrow(() -> new EntityNotFoundException(Address.class, id)));
    }

    public void deleteAddress(final Long addressId) {
        addressRepository.findById(addressId).orElseThrow(() -> new EntityNotFoundException(Address.class, addressId));
        addressRepository.deleteById(addressId);
    }
}
