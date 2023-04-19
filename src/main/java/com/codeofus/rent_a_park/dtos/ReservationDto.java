package com.codeofus.rent_a_park.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReservationDto {

    Integer id;

    Integer personId;

    Integer spotId;

    LocalDateTime createdAt;

    LocalDateTime reservedFrom;

    LocalDateTime reservedUntil;
}

