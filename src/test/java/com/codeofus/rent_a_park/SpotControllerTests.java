package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.controllers.SpotController;
import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import com.codeofus.rent_a_park.services.PersonService;
import com.codeofus.rent_a_park.services.SpotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

//TODO: fix the setUp
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpotControllerTests {

    Person person;
    SpotDto testSpot;
    Spot spot;

    @Autowired
    private SpotRepository spotRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private PersonService personService;
    @Autowired
    private SpotService spotService;
    @Autowired
    private ParkingMapper mapper;
    @Autowired
    private SpotController spotController;


    @BeforeEach
    public void setUp() {
        spotRepository.deleteAll();

        person = Person.builder()
                .firstName("First")
                .lastName("Last")
                .registration("1234")
                .build();
        personRepository.save(person);
        testSpot = SpotDto.builder()
                .address("Zvonimirova 16")
                .capacity(1)
                .renter(mapper.personToDto(person))
                .build();

        spot = mapper.toSpot(testSpot);
        spotRepository.save(spot); //repository not saving correctly
        List<Spot> list = spotRepository.findAll();
        spotRepository.findAll();
    }

    @Test
    public void testGetAllSpots() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page page = spotRepository.findAll(pageable);
        when(spotService.getAllSpots(pageable)).thenReturn(List.of(spot));
        assertEquals(1, spotController.getAllParkingSpots(0, 10, "id").size());
    }

}