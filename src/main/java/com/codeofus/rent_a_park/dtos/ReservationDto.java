package com.codeofus.rent_a_park.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationDto {

    Long id;

    PersonDto renter;

    SpotDto spot;

    ZonedDateTime createdAt;

    ZonedDateTime reservedFrom;

    ZonedDateTime reservedUntil;
}
