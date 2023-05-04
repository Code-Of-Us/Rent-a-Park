package com.codeofus.rent_a_park.kafka;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.kafka.producer.ReservationProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ReservationKafkaControllerTest extends IntegrationTest {
    static final String RESERVATIONS_API = "/reservations/reserve";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReservationProducer producer;

    @Test
    public void checkIfReservationSent() throws Exception {
        mockMvc.perform(post(RESERVATIONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ReservationDtoStub.createReservationSub().toString()))
                .andExpect(status().isOk());

        verify(producer, times(1)).sendReservation(ReservationDtoStub.createReservationSub());
    }
}
