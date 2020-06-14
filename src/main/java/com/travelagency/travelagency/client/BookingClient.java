package com.travelagency.travelagency.client;

import com.travelagency.travelagency.model.BookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookingClient {

    @Autowired
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/v1/bookings";

    public BookingDto[] getBookings() {

        ResponseEntity<BookingDto[]> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                BookingDto[].class
        );
        return response.getBody();
    }

    public void createBooking(BookingDto customerDto) {
        restTemplate.postForObject(URL, customerDto, BookingDto.class);
    }

    public void updateBooking(BookingDto customerDto) {
        restTemplate.put(URL, customerDto, BookingDto.class);
    }

    public void deleteBooking(BookingDto customerDto) {
        restTemplate.delete(
                URL + "/{customerId}", customerDto.getId()
        );
    }
}
