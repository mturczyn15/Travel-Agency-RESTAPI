package com.travelagency.travelagency.mapper;

import com.travelagency.travelagency.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public Booking map(final BookingDto bookingDto, final Customer customer, final TravelAgency travelAgency,
    final Hotel hotel) {
        return Booking.builder()
                .id(bookingDto.getId())
                .customer(customer)
                .travelAgency(travelAgency)
                .hotel(hotel)
                .price(bookingDto.getPrice())
                .startDate(bookingDto.getStartDate())
                .endDate(bookingDto.getEndDate())
                .paymentType(bookingDto.getPaymentType())
                .build();
    }

    public BookingDto mapToDto(final Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getCustomer().getId(),
                booking.getTravelAgency().getId(),
                booking.getHotel().getId(),
                booking.getPrice(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getPaymentType()
        );
    }

    public List<BookingDto> mapToDtoList(final List<Booking> bookingList) {
        return bookingList.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
