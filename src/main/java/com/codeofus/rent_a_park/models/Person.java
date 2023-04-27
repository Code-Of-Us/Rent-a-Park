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
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String firstName;
    String lastName;
    String registration;
    String email;

    public Person updatePerson(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.registration = person.getRegistration();
        this.email = person.getEmail();
        return this;
    }

}