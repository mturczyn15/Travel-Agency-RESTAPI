package com.travelagency.travelagency.client;

import com.travelagency.travelagency.model.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class AddressClient {

    @Autowired
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/v1/addresses";

    public AddressDto[] getAddresses() {

        ResponseEntity<AddressDto[]> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                AddressDto[].class
        );
        return response.getBody();
    }

    public AddressDto[] getAddressesByName(String name) {

        ResponseEntity<AddressDto[]> response = restTemplate.exchange(
                buildUri(name),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                AddressDto[].class
        );
        return response.getBody();
    }
    private URI buildUri(String filterText) {

        return UriComponentsBuilder.fromHttpUrl(URL + "/firstname")
                .queryParam("name", filterText)
                .build().encode().toUri();
    }

    public void createAddress(AddressDto customerDto) {
        restTemplate.postForObject(URL, customerDto, AddressDto.class);
    }

    public void updateAddress(AddressDto customerDto) {
        restTemplate.put(URL, customerDto, AddressDto.class);
    }

    public void deleteAddress(AddressDto customerDto) {
        restTemplate.delete(
                URL + "/{customerId}", customerDto.getId()
        );
    }
}

