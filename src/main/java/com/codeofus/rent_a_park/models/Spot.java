package com.codeofus.rent_a_park.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Spot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String address;
    LocalDateTime availability;
    Integer capacity;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Person renter;

    @ManyToOne
    @JoinColumn(name = "parker_id")
    Person parker;

}