package com.codeofus.rent_a_park.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReservationKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(ReservationKafkaProducer.class);
    private final String RESERVATION_TOPIC = "reservations";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public ReservationKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        logger.info("sending message to Kafka...");
        this.kafkaTemplate.send(RESERVATION_TOPIC, message);
    }

}
