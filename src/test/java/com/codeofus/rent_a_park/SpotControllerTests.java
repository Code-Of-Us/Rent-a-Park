//package com.codeofus.rent_a_park;
//
//import com.codeofus.rent_a_park.controllers.SpotController;
//import com.codeofus.rent_a_park.dtos.ParkingMapper;
//import com.codeofus.rent_a_park.dtos.PersonDto;
//import com.codeofus.rent_a_park.dtos.SpotDto;
//import com.codeofus.rent_a_park.models.Person;
//import com.codeofus.rent_a_park.models.Spot;
//import com.codeofus.rent_a_park.repositories.PersonRepository;
//import com.codeofus.rent_a_park.repositories.SpotRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
////TODO: fix the setUp
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class SpotControllerTests {
//
//    PersonDto testPerson;
//    Person person;
//    SpotDto testSpot;
//    Spot spot;
//
//    @Autowired
//    private SpotRepository spotRepository;
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private SpotController spotController;
//    @Autowired
//    private ParkingMapper mapper;
//
//    @BeforeEach
//    public void setUp() {
//        spotRepository.deleteAll();
//
//        testPerson = PersonDto.builder()
//                .firstName("First")
//                .lastName("Last")
//                .registration("1234")
//                .build();
//        person = mapper.toPerson(testPerson);
//        person = personRepository.save(person);
//
//        testSpot = SpotDto.builder()
//                .address("Zvonimirova 16")
//                .capacity(1)
//                .renter(mapper.toPerson(testPerson))
//                .build();
//
//        spot = mapper.toSpot(testSpot);
//        spot.setRenter(person);
//        spotRepository.save(spot); //ConstraintViolationException
//
//    }
//
//    @Test
//    public void testGetAllSpots() {
//
//    }
//
//}
