package com.travelagency.travelagency.service;

import com.travelagency.travelagency.domain.Address;
import com.travelagency.travelagency.domain.Customer;
import com.travelagency.travelagency.domain.CustomerDto;
import com.travelagency.travelagency.domain.EntityNotFoundException;
import com.travelagency.travelagency.mapper.CustomerMapper;
import com.travelagency.travelagency.repository.AddressRepository;
import com.travelagency.travelagency.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private AddressRepository addressRepository;

    public CustomerDto create(final CustomerDto customerDto) {
        Address address = addressRepository.findById(customerDto.getMainAddressId()).orElseThrow(() -> new EntityNotFoundException(Address.class, customerDto.getMainAddressId()));
        customerDto.setId(null);
        Customer customer = customerMapper.map(customerDto);
        return customerMapper.mapToDto(customerRepository.save(customer));
    }

    public CustomerDto update(final CustomerDto customerDto) {
        Address address = addressRepository.findById(customerDto.getMainAddressId()).orElseThrow(() -> new EntityNotFoundException(Address.class, customerDto.getMainAddressId()));
        customerRepository.findById(customerDto.getId()).orElseThrow(() -> new EntityNotFoundException(Customer.class, customerDto.getId()));
        return customerMapper.mapToDto(customerRepository.save(customerMapper.map(customerDto)));
    }

    public List<CustomerDto> getCustomers() {
        return customerMapper.mapToDtoList(customerRepository.findAll());
    }

    public CustomerDto getCustomer(final Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customerMapper.mapToDto(customer.orElseThrow(() -> new EntityNotFoundException(Customer.class, id)));
    }

    public void deleteCustomer(final Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException(Customer.class, customerId));
        customerRepository.deleteById(customerId);
    }
}
