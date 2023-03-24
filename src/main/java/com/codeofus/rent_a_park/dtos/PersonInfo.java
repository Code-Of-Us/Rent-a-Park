package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Person;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonInfo implements Serializable {
    Integer id;
    String firstName;
    String lastName;
    String registration;
}
