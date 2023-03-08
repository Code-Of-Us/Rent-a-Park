package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Spot;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto {
    int id;
    String firstName;
    String lastName;
    String registration;
    List<Spot> rentedSpots;
    List<Spot> parkingSpots;
}
