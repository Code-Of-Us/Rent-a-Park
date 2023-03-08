package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.controllers.PersonController;
import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.errors.BadRequestException;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonControllerTests {

    @Autowired
    private PersonService personService;
    @Autowired
    private PersonController personController;
    @Autowired
    private ParkingMapper mapper;

    @Test
    public void testAddNewPerson() throws BadRequestException, URISyntaxException {
        Person testPerson = Person.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .registration("ZG1234ZG")
                .rentedSpots(null)
                .parkingSpots(null)
                .build();
        personController.addPerson(mapper.personToDto(testPerson));
        assertEquals(1, personController.getAllPersons());
    }

}
