package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.dtos.CreateReservationDTO;
import com.codeofus.rent_a_park.dtos.ReservationDTO;
import com.codeofus.rent_a_park.models.Reservation;
import com.codeofus.rent_a_park.stubs.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationMapperTests extends IntegrationTest {

    Reservation reservation;
    ReservationDTO reservationDTO;
    CreateReservationDTO createReservationDTO;
    LocalDateTime date;

    @Autowired
    private ReservationMapper reservationMapper;

    @BeforeEach
    void setUp() {
        date = LocalDateTime.now();
        reservationDTO = ReservationDTOStub.givenReservationDtoStubBuilder().createdAt(date).build();
        createReservationDTO = CreateReservationDTOStub.givenCreateReservationDTOStubBuilder().createdAt(date).build();
        reservation = ReservationStub.givenReservationStubBuilder().createdAt(date).build();
    }

    @Test
    public void testReservationDTOToReservation() {
        Reservation mapperReservation = reservationMapper.reservationDTOtoReservation(reservationDTO);
        assertEquals(reservation.getCreatedAt(), mapperReservation.getCreatedAt());
    }

    @Test
    public void testReservationToReservationDTO() {
        ReservationDTO mapperReservation = reservationMapper.reservationToReservationDTO(reservation);
        assertEquals(reservationDTO.getCreatedAt(), mapperReservation.getCreatedAt());
    }

    @Test
    public void testCreateOrUpdateDTOtoReservation() {
        Reservation mapperReservation = reservationMapper.createOrUpdateDTOtoReservation(createReservationDTO);
        assertEquals(createReservationDTO.getCreatedAt(), mapperReservation.getCreatedAt());
    }

}
