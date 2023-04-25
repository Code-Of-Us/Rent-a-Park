package com.codeofus.rent_a_park.kafka.producer;

import com.codeofus.reservations.ReservationDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReservationKafkaProducer {
    static Logger logger = LoggerFactory.getLogger(ReservationKafkaProducer.class);
    String RESERVATION_TOPIC = "reservations";

    KafkaTemplate<String, ReservationDto> kafkaTemplate;

    public ReservationKafkaProducer(KafkaTemplate<String, ReservationDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReservation(ReservationDto reservationDto) {
        logger.info("Sending message: [{}]", reservationDto);
        ListenableFuture<SendResult<String, ReservationDto>> result = this.kafkaTemplate.send(RESERVATION_TOPIC, reservationDto);

        result.addCallback(
                successResult -> logger.info("Message sent successfully: [{}]", reservationDto),
                ex -> logger.error("Failed to send message: [{}]", reservationDto, ex));
    }
}
