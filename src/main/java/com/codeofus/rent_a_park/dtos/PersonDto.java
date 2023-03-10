package com.codeofus.rent_a_park.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String registration;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<SpotDto> rentedSpots;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<SpotDto> parkingSpots;

}