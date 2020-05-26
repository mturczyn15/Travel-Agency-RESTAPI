package com.travelagency.travelagency.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "TRAVELAGENCIES")
public class TravelAgency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRAVEL_AGENCY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ROOM")
    private String room;
}
