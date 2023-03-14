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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String firstName;
    String lastName;
    String registration;

    @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Transient
    List<Spot> rentedSpots = new ArrayList<>();

    @OneToMany(mappedBy = "parker", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Transient
    List<Spot> parkingSpots = new ArrayList<>();

    public Person UpdatePerson(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.registration = person.getRegistration();
        return this;
    }
}