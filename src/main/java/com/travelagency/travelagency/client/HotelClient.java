package com.travelagency.travelagency.client;

import com.travelagency.travelagency.model.HotelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class HotelClient {

    @Autowired
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/v1/hotels";

    public HotelDto[] getHotels() {

        ResponseEntity<HotelDto[]> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                HotelDto[].class
        );
        return response.getBody();
    }

    public HotelDto getHotel(Integer id) {

        ResponseEntity<HotelDto> response = restTemplate.exchange(URL + "/{hotelId}",
                HttpMethod.GET,
                HttpEntity.EMPTY,
                HotelDto.class,
                id

        );
        return response.getBody();
    }

    public HotelDto[] getHotelsByName(String name) {

        ResponseEntity<HotelDto[]> response = restTemplate.exchange(
                buildUri(name),
                HttpMethod.GET,
                HttpEntity.EMPTY,
                HotelDto[].class
        );
        return response.getBody();
    }
    private URI buildUri(String filterText) {

        return UriComponentsBuilder.fromHttpUrl(URL + "/name")
                .queryParam("name", filterText)
                .build().encode().toUri();
    }

    public void createHotel(HotelDto hotelDto) {
        restTemplate.postForObject(URL, hotelDto, HotelDto.class);
    }

    public void updateHotel(HotelDto hotelDto) {
        restTemplate.put(URL, hotelDto, HotelDto.class);
    }

    public void deleteHotel(HotelDto hotelDto) {
        restTemplate.delete(
                URL + "/{hotelId}", hotelDto.getId()
        );
    }
}
