package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.ReservationDto;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.mappers.ReservationMapper;
import com.codeofus.rent_a_park.mappers.SpotMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Reservation;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import com.codeofus.rent_a_park.repositories.ReservationRepository;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.hasItem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTests extends IntegrationTest {

    static final String RESERVATIONS_API = "/api/v1/reservations";
    static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.parse("2023-12-03T10:15:30+01:00");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    PersonMapper personMapper;

    @Autowired
    SpotMapper spotMapper;

    Reservation reservation;

    @BeforeEach
    public void setUp() {
        reservationRepository.deleteAll();
        ReservationDto reservationDto = createReservationDto();
        reservation = reservationMapper.reservationDTOtoReservation(reservationDto);
        reservationRepository.save(reservation);
    }

    @AfterAll
    void cleanUp() {
        reservationRepository.deleteAll();
    }

    public ReservationDto createReservationDto() {
        Person person = personRepository.save(personMapper.personDTOtoPerson(PersonDto.builder().firstName(PersonControllerTests.DEFAULT_FIRSTNAME).build()));
        Spot spot = spotRepository.save(spotMapper.spotDTOtoSpot(SpotDto.builder().address(SpotControllerTests.DEFAULT_ADDRESS).renter(personMapper.personToPersonDTO(person)).build()));
        return ReservationDto.builder().person(personMapper.personToPersonDTO(person)).spot(spotMapper.spotToSpotDTO(spot)).createdAt(ZONED_DATE_TIME).build();
    }

    @Test
    public void testCreateReservation() throws Exception {
        int sizeBeforeAdding = reservationRepository.findAll().size();
        ReservationDto reservationDto = createReservationDto();

        mockMvc.perform(post(RESERVATIONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(reservationDto)))
                .andExpect(status().isOk());

        List<Reservation> reservationList = reservationRepository.findAll();
        assertThat(reservationList).hasSize(sizeBeforeAdding + 1);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(RESERVATIONS_API))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(ZONED_DATE_TIME.toString())));
    }

    @Test
    public void testGetReservation() throws Exception {
        mockMvc.perform(get(RESERVATIONS_API + "/{id}", reservation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.createdAt").value(ZONED_DATE_TIME.toString()));
    }

    @Test
    public void testDeleteReservation() throws Exception {
        int sizeBeforeDeleting = reservationRepository.findAll().size();

        mockMvc.perform(delete(RESERVATIONS_API + "/{id}", reservation.getId()))
                .andExpect(status().isOk());

        assertEquals(sizeBeforeDeleting - 1, reservationRepository.findAll().size());
    }

    @Test
    public void testUpdateReservation() throws Exception {
        Person person = personRepository.save(personMapper.personDTOtoPerson(PersonDto.builder().firstName(PersonControllerTests.UPDATED_FIRSTNAME).build()));
        ReservationDto updatedReservationDto = ReservationDto.builder().id(reservation.getId()).person(personMapper.personToPersonDTO(person)).build();

        mockMvc.perform(put(RESERVATIONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedReservationDto)))
                .andExpect(status().isOk());

        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        assertEquals(person.getFirstName(), updatedReservation.getPerson().getFirstName());
    }

}
