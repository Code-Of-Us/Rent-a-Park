package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Person;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpotDto {

    int id;
    String address;
    LocalDateTime availability;
    Integer capacity;
    Person renter;
    Person parker;

}