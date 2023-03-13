//package com.codeofus.rent_a_park;
//
//import com.codeofus.rent_a_park.controllers.PersonController;
//import com.codeofus.rent_a_park.dtos.ParkingMapper;
//import com.codeofus.rent_a_park.dtos.PersonDto;
//import com.codeofus.rent_a_park.models.Person;
//import com.codeofus.rent_a_park.repositories.PersonRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class PersonControllerTests {
//
//    PersonDto testPerson;
//    Person person;
//
//    @Autowired
//    private PersonRepository personRepository;
//    @Autowired
//    private PersonController personController;
//    @Autowired
//    private ParkingMapper mapper;
//
//    @BeforeEach
//    public void setUp() {
//        personRepository.deleteAll();
//        testPerson = PersonDto.builder()
//                .firstName("First")
//                .lastName("Last")
//                .registration("1234")
//                .parkingSpots(null)
//                .rentedSpots(null)
//                .build();
//        person = mapper.toPerson(testPerson);
//        personRepository.save(person);
//    }
//
//    @Test
//    public void testGetAllPersons() {
//        assertEquals(1, personController.getAllPersons(0, 10, "id").size());
//    }
//
//    @Test
//    public void testAddNewPerson() {
//        Person testPerson = Person.builder()
//                .firstName("FirstName")
//                .lastName("LastName")
//                .registration("ZG1234ZG")
//                .rentedSpots(null)
//                .parkingSpots(null)
//                .build();
//        personController.addPerson(mapper.personToDto(testPerson));
//        assertEquals(2, personController.getAllPersons(0, 10, "id").size());
//    }
//
//    @Test
//    public void testDeletePerson() {
//        personController.deletePerson(testPerson.getId());
//        assertEquals(1, personController.getAllPersons(0, 10, "id").size());
//    }
//
//}
