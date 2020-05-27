package com.travelagency.travelagency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class BookingDto {
    private Long id;
    private Long customerId;
    private Long travelAgencyId;
    private Long hotelId;
    private BigDecimal price;
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentType paymentType;
}
