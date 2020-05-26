package com.travelagency.travelagency.domain;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto {

    private Long id;
    private Long addressId;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;


}
