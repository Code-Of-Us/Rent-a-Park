package com.codeofus.rent_a_park.kafka.producer;

import com.codeofus.reservations.ReservationDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ReservationProducer {
    final KafkaTemplate<String, ReservationDto> kafkaTemplate;
    @Value("${spring.kafka.reservation-topic}")
    String topic;

    public ReservationProducer(KafkaTemplate<String, ReservationDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReservation(ReservationDto reservationDto) {
        log.info("Sending message: [{}]", reservationDto);
        ListenableFuture<SendResult<String, ReservationDto>> result = kafkaTemplate.send(topic, reservationDto);

        result.addCallback(
                successResult -> log.info("Message sent successfully: [{}]", reservationDto),
                ex -> log.error("Failed to send message: [{}]", reservationDto, ex));
    }
}
