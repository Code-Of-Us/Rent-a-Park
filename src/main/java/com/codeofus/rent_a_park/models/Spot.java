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
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Spot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String address;

    String zone;

    @ManyToOne
    @JoinColumn(name = "renter_id", nullable = false)
    Person renter;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Reservation> reservation = new ArrayList<>();

    public Spot updateSpot(Spot spot) {
        this.address = spot.getAddress();
        this.zone = spot.getZone();
        this.renter = spot.getRenter();
        return this;
    }

}