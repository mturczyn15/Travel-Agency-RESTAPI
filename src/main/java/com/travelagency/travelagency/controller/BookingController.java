package com.travelagency.travelagency.controller;

import com.travelagency.travelagency.domain.BookingDto;
import com.travelagency.travelagency.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/travelagency/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @RequestMapping(method = RequestMethod.GET, value = "getBookings")
    public List<BookingDto> getBookings() {
        return bookingService.getBookings();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBooking")
    public BookingDto getBooking(@RequestParam Long bookingId) {
        return bookingService.getBooking(bookingId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBooking")
    public void deleteBooking(@RequestParam Long bookingId) {
        bookingService.deleteBooking(bookingId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "updateBooking")
    public BookingDto updateBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.update(bookingDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "createBooking", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createBooking(@RequestBody BookingDto bookingDto) {
        bookingService.create(bookingDto);
    }

}
