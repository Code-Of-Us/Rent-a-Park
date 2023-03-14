package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    ParkingMapper mapper;

    private SpotDto createSpotDto() {
        return SpotDto.builder().address(DEFAULT_ADDRESS).capacity(DEFAULT_CAPACITY).build();
    }

    private Spot createSpotEntity() {
        Person person = personRepository.save(mapper.toPerson(PersonDto.builder().firstName(PersonControllerTests.DEFAULT_FIRSTNAME).build()));
        SpotDto spotDto = createSpotDto();
        spotDto.setRenter(mapper.personToDto(person));
        return spotRepository.save(mapper.toSpot(spotDto));
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

    //TODO: Add more tests
}