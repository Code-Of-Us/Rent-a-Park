package com.codeofus.rent_a_park.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto {

    Integer id;
    String firstName;
    String lastName;
    String registration;
    List<SpotDto> rentedSpots = new ArrayList<>();
    List<SpotDto> parkingSpots = new ArrayList<>();

}