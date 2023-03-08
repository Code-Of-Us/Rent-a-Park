package com.codeofus.rent_a_park.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String firstName;
    String lastName;
    String registration;

    @JsonIgnore
    @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> rentedSpots = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "parker", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> parkingSpots = new ArrayList<>();

}
