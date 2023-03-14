package com.codeofus.rent_a_park.dtos;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto implements Serializable {

    Integer id;
    String firstName;
    String lastName;
    String registration;
    List<SpotDto> rentedSpots;
    List<SpotDto> parkingSpots;

}