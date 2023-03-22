package com.codeofus.rent_a_park.models;

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
@ToString(exclude = {"rentedSpots", "parkingSpots"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String firstName;
    String lastName;
    String registration;

    @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> rentedSpots = new ArrayList<>();

    @OneToMany(mappedBy = "parker", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Spot> parkingSpots = new ArrayList<>();

    public Person updatePerson(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.registration = person.getRegistration();
        return this;
    }
}