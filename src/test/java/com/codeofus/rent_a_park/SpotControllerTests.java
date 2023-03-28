package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SpotControllerTests extends IntegrationTest {

    static final String SPOTS_API = "/api/v1/parking";

    static final String DEFAULT_ADDRESS = "Address 111";
    static final Integer DEFAULT_CAPACITY = 1;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SpotRepository spotRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ParkingMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    Spot spot;

    @AfterAll
    void cleanUp() {
        personRepository.deleteAll();
        spotRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        SpotDto spotDto = createSpotDto();
        spot = mapper.toSpot(spotDto);
        spotRepository.save(spot);
    }

    private SpotDto createSpotDto() {
        Person person = personRepository.save(mapper.toPerson(PersonDto.builder().firstName(PersonControllerTests.DEFAULT_FIRSTNAME).build()));
        return SpotDto.builder().address(DEFAULT_ADDRESS).capacity(DEFAULT_CAPACITY).renter(mapper.personToDto(person)).build();
    }

    @Test
    public void testGetAllSpots() throws Exception {
        mockMvc.perform(get(SPOTS_API))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    public void testAddNewParkingSpot() throws Exception {
        int sizeBeforeAdding = spotRepository.findAll().size();
        SpotDto spotDto = createSpotDto();

        mockMvc.perform(post(SPOTS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(spotDto)))
                .andExpect(status().isOk())
                .andReturn();

        List<Spot> spotList = spotRepository.findAll();
        assertThat(spotList).hasSize(sizeBeforeAdding + 1);
        Spot spot = spotList.get(spotList.size() - 1);
        assertEquals(spot.getCapacity(), DEFAULT_CAPACITY);
        assertEquals(spot.getAddress(), DEFAULT_ADDRESS);
    }

    @Test
    public void testDeleteParking() throws Exception {
        mockMvc.perform(delete(SPOTS_API + "/{id}", spot.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void testCancelReservation() throws Exception {
        Person parker = personRepository.save(Person.builder().firstName("Parker").build());
        spot.setParker(parker);

        mockMvc.perform(post(SPOTS_API + "/{id}/cancel/{parkerId}", spot.getId(), spot.getParker().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(spot)))
                .andExpect(status().isOk());
        List<Spot> spotList = spotRepository.findAll();
        Spot spot = spotList.get(spotList.size() - 1);
        assertNull(spot.getParker());
    }

    @Test
    public void testReserveParking() throws Exception {
        Person parker = personRepository.save(Person.builder().firstName("Parker").build());
        mockMvc.perform(post(SPOTS_API + "/{id}/reserve/{parkerId}", spot.getId(), parker.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(spot)))
                .andExpect(status().isOk());
        List<Spot> spotList = spotRepository.findAll();
        Spot spot = spotList.get(spotList.size() - 1);
        assertEquals("Parker", spot.getParker().getFirstName());
    }

}