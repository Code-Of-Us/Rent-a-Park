package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@FieldDefaults(level = PRIVATE)
@AutoConfigureMockMvc
class PersonControllerTests extends IntegrationTest {

    static final String PERSONS_API = "/api/v1/persons";
    
    static final String DEFAULT_FIRSTNAME = "Firstname";
    static final String DEFAULT_LASTNAME = "Lastname";
    static final String DEFAULT_REGISTRATION = "ZG1234-SO";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ParkingMapper mapper;

    private PersonDto createPersonDto() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName(DEFAULT_FIRSTNAME);
        personDto.setLastName(DEFAULT_LASTNAME);
        personDto.setRegistration(DEFAULT_REGISTRATION);
        return personDto;
    }

    private Person createPersonEntity() {
        PersonDto personDto = createPersonDto();
        return personRepository.save(mapper.toPerson(personDto));
    }

    @Test
    void addNewPerson() throws Exception {
        int sizeBeforeAdding = personRepository.findAll().size();

        PersonDto personDto = createPersonDto();
        mockMvc.perform(post(PERSONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(personDto)))
                .andExpect(status().isOk())
                .andReturn();

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(sizeBeforeAdding + 1);
        Person person = personList.get(personList.size() - 1);
        assertEquals(person.getFirstName(), DEFAULT_FIRSTNAME);
        assertEquals(personDto.getLastName(), DEFAULT_LASTNAME);
    }

    @Test
    public void getAllPersons() throws Exception {
        createPersonEntity();

        mockMvc.perform(get(PERSONS_API))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRSTNAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.[*].registration").value(hasItem(DEFAULT_REGISTRATION)));
    }

    @Test
    public void deletePerson() throws Exception {
        Person person = createPersonEntity();

        mockMvc.perform(delete(PERSONS_API + "/{id}", person.getId()))
                .andExpect(status().isOk());
    }
}
