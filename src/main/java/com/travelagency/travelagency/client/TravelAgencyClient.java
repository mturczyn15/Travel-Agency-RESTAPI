package com.travelagency.travelagency.client;

import com.travelagency.travelagency.model.TravelAgencyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class TravelAgencyClient {

    @Autowired
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/v1/travelAgencies";

    public TravelAgencyDto[] getTravelAgencies() {

        ResponseEntity<TravelAgencyDto[]> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                TravelAgencyDto[].class
        );
        return response.getBody();
    }

    public TravelAgencyDto[] getTravelAgenciesByName(String name) {

        ResponseEntity<TravelAgencyDto[]> response = restTemplate.exchange(
                buildUri(name),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                TravelAgencyDto[].class
        );
        return response.getBody();
    }
    private URI buildUri(String filterText) {

        return UriComponentsBuilder.fromHttpUrl(URL + "/name")
                .queryParam("name", filterText)
                .build().encode().toUri();
    }

    public void createTravelAgency(TravelAgencyDto travelAgencyDto) {
        restTemplate.postForObject(URL, travelAgencyDto, TravelAgencyDto.class);
    }

    public void updateTravelAgency(TravelAgencyDto travelAgencyDto) {
        restTemplate.put(URL, travelAgencyDto, TravelAgencyDto.class);
    }

    public void deleteTravelAgency(TravelAgencyDto travelAgencyDto) {
        restTemplate.delete(
                URL + "/{travelAgencyId}", travelAgencyDto.getId()
        );
    }
}