package com.travelagency.travelagency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class AddressDto {
    private Long id;
    private Long customerId;
    private String street;
    private String houseNumber;
    private String city;
    private String zipCode;
    private String phoneNumber;
}
