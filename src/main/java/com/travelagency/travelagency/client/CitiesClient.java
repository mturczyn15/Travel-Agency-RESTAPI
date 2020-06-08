package com.travelagency.travelagency.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CitiesClient {

    @Autowired
    private RestTemplate restTemplate;

    public String[] getCities() {

        ResponseEntity<String[]> cities = restTemplate.exchange(
                "http://localhost:8080/v1/getCities",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                String[].class
        );
        return cities.getBody();
    }
}
