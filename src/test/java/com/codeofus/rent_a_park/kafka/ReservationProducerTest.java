package com.codeofus.rent_a_park.kafka;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.kafka.producer.ReservationProducer;
import com.codeofus.reservations.ReservationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

public class ReservationProducerTest extends IntegrationTest {
    @Value("${spring.kafka.reservation-topic}" )
    String topic;

    @Autowired
    ReservationProducer producer;

    @MockBean
    KafkaTemplate<String, ReservationDto> kafkaTemplate;

    @Test
    public void checkIfMessageSentToKafka() {
        ReservationDto reservationToBeSent = ReservationDtoStub.createReservationSub();
        ListenableFuture<SendResult<String, ReservationDto>> future = mock(ListenableFuture.class);
        when(kafkaTemplate.send(topic, reservationToBeSent)).thenReturn(future);

        producer.sendReservation(reservationToBeSent);

        await()
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> verify(kafkaTemplate, times(1)).send(topic, reservationToBeSent));
    }
}
