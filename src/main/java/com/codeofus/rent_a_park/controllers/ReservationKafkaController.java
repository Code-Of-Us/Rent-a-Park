package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.kafka.producer.ReservationKafkaProducer;
import com.codeofus.reservations.ReservationDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations" )
public class ReservationKafkaController {
    private final ReservationKafkaProducer reservationKafkaProducer;

    public ReservationKafkaController(ReservationKafkaProducer reservationKafkaProducer) {
        this.reservationKafkaProducer = reservationKafkaProducer;
    }

    @PostMapping("/reserve" )
    public void createReservation(@RequestBody ReservationDto reservation) {
        this.reservationKafkaProducer.sendReservation(reservation);
    }
}
