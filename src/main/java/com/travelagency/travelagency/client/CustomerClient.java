package com.travelagency.travelagency.client;

import com.travelagency.travelagency.model.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class CustomerClient {

    @Autowired
    private RestTemplate restTemplate;

    public CustomerDto[] getCustomers() {

        ResponseEntity<CustomerDto[]> response = restTemplate.exchange(
                "http://localhost:8080/v1/customers",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                CustomerDto[].class
        );
        return response.getBody();
    }

    public CustomerDto[] getCustomersByName(String name) {

        ResponseEntity<CustomerDto[]> response = restTemplate.exchange(
                buildUri(name),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                CustomerDto[].class
        );
        return response.getBody();
    }
    private URI buildUri(String filterText) {

        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/customers/firstname")
                .queryParam("name", filterText)
                .build().encode().toUri();
    }
}