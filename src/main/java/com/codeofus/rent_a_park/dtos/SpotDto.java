package com.codeofus.rent_a_park.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpotDto {

    Integer id;
    String address;
    LocalDateTime availability;
    Integer capacity;
    PersonDto renter;
    PersonDto parker;

}