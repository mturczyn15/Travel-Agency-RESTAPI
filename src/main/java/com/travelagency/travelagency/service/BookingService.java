package com.travelagency.travelagency.service;

import com.travelagency.travelagency.domain.*;
import com.travelagency.travelagency.mapper.BookingMapper;
import com.travelagency.travelagency.repository.BookingRepository;
import com.travelagency.travelagency.repository.CustomerRepository;
import com.travelagency.travelagency.repository.HotelRepository;
import com.travelagency.travelagency.repository.TravelAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingMapper bookingMapper;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TravelAgencyRepository travelAgencyRepository;
    @Autowired
    private HotelRepository hotelRepository;

    public BookingDto create(final BookingDto bookingDto) {
        Customer customer = customerRepository.findById(bookingDto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException(Customer.class, bookingDto.getCustomerId()));
        TravelAgency travelAgency = travelAgencyRepository.findById(bookingDto.getTravelAgencyId()).orElseThrow(() -> new EntityNotFoundException(TravelAgency.class, bookingDto.getTravelAgencyId()));
        Hotel hotel = hotelRepository.findById(bookingDto.getHotelId()).orElseThrow(() -> new EntityNotFoundException(Hotel.class, bookingDto.getHotelId()));
        bookingDto.setId(null);
        Booking booking = bookingMapper.map(bookingDto, customer, travelAgency, hotel);
        return bookingMapper.mapToDto(bookingRepository.save(booking));
    }

    public BookingDto update(final BookingDto bookingDto) {
        bookingRepository.findById(bookingDto.getId()).orElseThrow(() -> new EntityNotFoundException(Booking.class, bookingDto.getId()));
        Customer customer = customerRepository.findById(bookingDto.getCustomerId()).orElseThrow(() -> new EntityNotFoundException(Customer.class, bookingDto.getCustomerId()));
        TravelAgency travelAgency = travelAgencyRepository.findById(bookingDto.getTravelAgencyId()).orElseThrow(() -> new EntityNotFoundException(TravelAgency.class, bookingDto.getTravelAgencyId()));
        Hotel hotel = hotelRepository.findById(bookingDto.getHotelId()).orElseThrow(() -> new EntityNotFoundException(Hotel.class, bookingDto.getHotelId()));
        return bookingMapper.mapToDto(bookingRepository.save(bookingMapper.map(bookingDto, customer, travelAgency, hotel)));
    }

    public List<BookingDto> getBookings() {
        return bookingMapper.mapToDtoList(bookingRepository.findAll());
    }

    public BookingDto getBooking(final Long id) {
        Optional<Booking> booking = bookingRepository.findById(id);
        return bookingMapper.mapToDto(booking.orElseThrow(() -> new EntityNotFoundException(Booking.class, id)));
    }

    public void deleteBooking(final Long bookingId) {
        bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException(Booking.class, bookingId));
        bookingRepository.deleteById(bookingId);
    }
}
