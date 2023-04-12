package com.codeofus.rent_a_park.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReservationDTO {

    Integer personId;

    Integer spotId;

    LocalDateTime createdAt;

    LocalDateTime reservedFrom;

    LocalDateTime reservedUntil;
}
