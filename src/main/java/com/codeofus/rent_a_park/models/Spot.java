package com.codeofus.rent_a_park.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

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

    String parkingZone;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Person renter;

    public Spot updateSpot(Spot spot) {
        this.address = spot.getAddress();
        this.parkingZone = spot.getParkingZone();
        this.renter = spot.getRenter();
        return this;
    }

}