package com.codeofus.rent_a_park.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String firstName;
    String lastName;
    String registration;

    @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> rentedSpots;

    @OneToMany(mappedBy = "parker", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> parkingSpots;

}
