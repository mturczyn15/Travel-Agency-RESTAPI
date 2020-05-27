package com.travelagency.travelagency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class HotelDto {

    private Long id;
    private String name;
    private String city;
    private String stars;
    private String phoneNumber;
}
