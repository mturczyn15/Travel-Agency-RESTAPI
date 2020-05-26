package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.domain.Address;
import com.travelagency.travelagency.domain.AddressDto;
import com.travelagency.travelagency.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressMapper {
    public Address map(final AddressDto addressDto, final Customer customer) {
        return Address.builder()
                .id(addressDto.getId())
                .customer(customer)
                .street(addressDto.getStreet())
                .houseNumber(addressDto.getHouseNumber())
                .zipCode(addressDto.getZipCode())
                .city(addressDto.getCity())
                .phoneNumber(addressDto.getPhoneNumber())
                .build();
    }

    public AddressDto mapToDto(final Address address) {
        return new AddressDto(
                address.getId(),
                address.getCustomer().getId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getZipCode(),
                address.getCity(),
                address.getPhoneNumber()
        );
    }

    public List<AddressDto> mapToDtoList(final List<Address> addressList) {
        return addressList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
