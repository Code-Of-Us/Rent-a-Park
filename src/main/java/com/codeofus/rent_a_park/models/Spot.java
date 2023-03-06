package com.codeofus.rent_a_park.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String address;
    LocalDateTime availability;
    Integer capacity;

    @ManyToOne
    @JoinColumn(name="renter", nullable = false)
    User renter;

    @ManyToOne
    @JoinColumn(name="parker")
    User parker;

}