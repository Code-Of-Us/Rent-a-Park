package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.dtos.CreateReservationDTO;
import com.codeofus.rent_a_park.dtos.PersonDTO;
import com.codeofus.rent_a_park.dtos.ReservationDTO;
import com.codeofus.rent_a_park.dtos.SpotDTO;
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
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.codeofus.rent_a_park.controllers.PersonControllerTests.UPDATED_FIRSTNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTests extends IntegrationTest {

    static final String RESERVATIONS_API = "/api/v1/reservations";
    static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2023, 12, 3, 10, 15);
    static final String LOCAL_DATE_TIME_STRING = "2023-12-03T10:15:00";

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

    Person person;

    Spot spot;

    @BeforeEach
    public void setUp() {
        reservationRepository.deleteAll();

        person = personRepository.save(personMapper.personDTOtoPerson(PersonDTO.builder().firstName(PersonControllerTests.DEFAULT_FIRSTNAME).build()));
        spot = spotRepository.save(spotMapper.spotDTOtoSpot(SpotDTO.builder().address(SpotControllerTests.DEFAULT_ADDRESS).renter(personMapper.personToPersonDTO(person)).build()));

        ReservationDTO reservationDto = createReservationDto();
        reservation = reservationMapper.reservationDTOtoReservation(reservationDto);
        reservationRepository.save(reservation);
    }

    @AfterAll
    void cleanUp() {
        reservationRepository.deleteAll();
    }

    public ReservationDTO createReservationDto() {
        return ReservationDTO.builder().person(personMapper.personToPersonDTO(person)).spot(spotMapper.spotToSpotDTO(spot)).createdAt(LOCAL_DATE_TIME).build();
    }

    public CreateReservationDTO createCreateReservationDto() {
        return CreateReservationDTO.builder().personId(person.getId()).spotId(spot.getId()).createdAt(LOCAL_DATE_TIME).build();
    }

    @Test
    public void testCreateReservation() throws Exception {
        int sizeBeforeAdding = reservationRepository.findAll().size();
        CreateReservationDTO reservationDto = createCreateReservationDto();

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
                .andExpect(jsonPath("$.content.[*].createdAt").value(hasItem(LOCAL_DATE_TIME_STRING)));
    }

    @Test
    public void testGetReservation() throws Exception {
        mockMvc.perform(get(RESERVATIONS_API + "/{id}", reservation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.createdAt").value(LOCAL_DATE_TIME_STRING));
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
        Person person = personRepository.save(personMapper.personDTOtoPerson(PersonDTO.builder().firstName(UPDATED_FIRSTNAME).build()));
        ReservationDTO updatedReservationDTO = ReservationDTO.builder().id(reservation.getId()).person(personMapper.personToPersonDTO(person)).build();

        mockMvc.perform(put(RESERVATIONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedReservationDTO)))
                .andExpect(status().isOk());

        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).get();
        assertEquals(UPDATED_FIRSTNAME, updatedReservation.getPerson().getFirstName());
    }

}
