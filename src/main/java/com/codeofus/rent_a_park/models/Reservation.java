package com.codeofus.rent_a_park.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    Person person;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    Spot spot;

    ZonedDateTime createdAt;
    ZonedDateTime reservedFrom;
    ZonedDateTime reservedUntil;

    public Reservation updateReservation(Reservation reservation) {
        this.person = reservation.getPerson();
        this.spot = reservation.getSpot();
        this.reservedFrom = reservation.getReservedFrom();
        this.reservedUntil = reservation.getReservedUntil();
        return this;
    }
}
