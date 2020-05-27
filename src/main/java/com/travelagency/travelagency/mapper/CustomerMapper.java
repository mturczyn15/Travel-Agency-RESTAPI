package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.domain.Customer;
import com.travelagency.travelagency.domain.CustomerDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {
    public Customer map(final CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.getId())
                .mainAddressId(customerDto.getMainAddressId())
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .login(customerDto.getLogin())
                .password(customerDto.getPassword())
                .email(customerDto.getEmail())
                .build();
    }

    public CustomerDto mapToDto(final Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getMainAddressId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getLogin(),
                customer.getPassword(),
                customer.getEmail());
    }

    public List<CustomerDto> mapToDtoList(final List<Customer> customerList) {
        return customerList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
