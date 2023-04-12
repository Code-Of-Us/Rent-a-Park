package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.dtos.PersonDTO;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.stubs.PersonDTOStub;
import com.codeofus.rent_a_park.stubs.PersonStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonMapperTests extends IntegrationTest {

    @Autowired
    private PersonMapper personMapper;

    Person person;
    PersonDTO personDTO;

    @BeforeEach
    void setUp() {
        personDTO = PersonDTOStub.givenPersonDtoStubBuilder().firstName("Test").lastName("Tester").registration("ZG0000").build();
        person = PersonStub.givenPersonStubBuilder().firstName("Test").lastName("Tester").registration("ZG0000").build();
    }

    @Test
    public void testPersonDtoToPerson() {
        Person mapperPerson = personMapper.personDTOtoPerson(personDTO);
        assertEquals(person.getFirstName(), mapperPerson.getFirstName());
        assertEquals(person.getLastName(), mapperPerson.getLastName());
        assertEquals(person.getRegistration(), mapperPerson.getRegistration());
    }

    @Test
    public void testPersonToPersonDto() {
        PersonDTO mapperPersonDTO = personMapper.personToPersonDTO(person);
        assertEquals(personDTO.getFirstName(), mapperPersonDTO.getFirstName());
        assertEquals(personDTO.getLastName(), mapperPersonDTO.getLastName());
        assertEquals(personDTO.getRegistration(), mapperPersonDTO.getRegistration());
    }

    @Test
    public void testPersonListToPersonDTOList() {
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        List<PersonDTO> mapperPersonDTOList = personMapper.personListToPersonDTOList(personList);
        assertEquals(person.getFirstName(), mapperPersonDTOList.get(0).getFirstName());
        assertEquals(person.getLastName(), mapperPersonDTOList.get(0).getLastName());
        assertEquals(person.getRegistration(), mapperPersonDTOList.get(0).getRegistration());
    }

}
