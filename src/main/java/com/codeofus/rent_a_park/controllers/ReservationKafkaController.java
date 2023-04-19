package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ReservationDto;
import com.codeofus.rent_a_park.kafka.producer.ReservationKafkaProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class ReservationKafkaController {

    private final ReservationKafkaProducer reservationKafkaProducer;

    private final ObjectMapper objectMapper;

    public ReservationKafkaController(ReservationKafkaProducer reservationKafkaProducer, ObjectMapper objectMapper) {
        this.reservationKafkaProducer = reservationKafkaProducer;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam String message) {
        this.reservationKafkaProducer.sendMessage(message);
    }

    @PostMapping("/reserve")
    public void createReservation(@RequestBody ReservationDto reservation) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(reservation);
        this.reservationKafkaProducer.sendMessage(json);
    }

}
