package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.SpotDto;

import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.mappers.SpotMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
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
    SpotMapper spotMapper;

    @Autowired
    PersonMapper personMapper;

    @Autowired
    ObjectMapper objectMapper;

    private SpotDto createSpotDto() {
        Person person = personRepository.save(personMapper.personDTOtoPerson(PersonDto.builder().firstName(PersonControllerTests.DEFAULT_FIRSTNAME).build()));
        return SpotDto.builder().address(DEFAULT_ADDRESS).renter(personMapper.personToPersonDTO(person)).build();
    }

    private Spot createSpotEntity() {
        SpotDto spotDto = createSpotDto();
        return spotRepository.save(spotMapper.spotDTOtoSpot(spotDto));
    }

    @Test
    public void testGetAllSpots() throws Exception {
        createSpotEntity();

        mockMvc.perform(get(SPOTS_API))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    // TODO: remove the following methods to reservation controller test, add tests for get, post, put, delete

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
        assertEquals(spot.getAddress(), DEFAULT_ADDRESS);
    }

    @Test
    public void testDeleteParking() throws Exception {
        Spot spot = createSpotEntity();
        mockMvc.perform(delete(SPOTS_API + "/{id}", spot.getId()))
                .andExpect(status().isOk());
    }
}