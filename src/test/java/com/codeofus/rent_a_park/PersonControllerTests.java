package com.codeofus.rent_a_park;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonControllerTests extends IntegrationTest {

    static final String PERSONS_API = "/api/v1/persons";

    static final String DEFAULT_FIRSTNAME = "Firstname";
    static final String DEFAULT_LASTNAME = "Lastname";
    static final String DEFAULT_REGISTRATION = "ZG1234-SO";
    static final String UPDATED_FIRSTNAME = "Updated-Firstname";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonMapper personMapper;

    @Autowired
    ObjectMapper objectMapper;

    @AfterAll
    void cleanUp() {
        personRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    public Person createAndSavePersonEntity() {
        PersonDto personDto = createPersonDto();
        return personRepository.save(personMapper.personDTOtoPerson(personDto));
    }

    private PersonDto createPersonDto() {
        return PersonDto.builder()
                .firstName(DEFAULT_FIRSTNAME)
                .lastName(DEFAULT_LASTNAME)
                .registration(DEFAULT_REGISTRATION)
                .build();
    }

    @Test
    void testCreatePerson() throws Exception {
        int sizeBeforeAdding = personRepository.findAll().size();

        PersonDto personDto = createPersonDto();
        mockMvc.perform(post(PERSONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(personDto)))
                .andExpect(status().isOk())
                .andReturn();

        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(sizeBeforeAdding + 1);
        Person person = personList.get(personList.size() - 1);
        assertEquals(person.getFirstName(), DEFAULT_FIRSTNAME);
        assertEquals(personDto.getLastName(), DEFAULT_LASTNAME);
    }

    @Test
    public void testGetPerson() throws Exception {
        Person person = createAndSavePersonEntity();
        mockMvc.perform(get(PERSONS_API + "/{id}", person.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRSTNAME))
                .andExpect(jsonPath("$.lastName").value(DEFAULT_LASTNAME))
                .andExpect(jsonPath("$.registration").value(DEFAULT_REGISTRATION));
    }

    @Test
    public void testGetAllPersons() throws Exception {
        createAndSavePersonEntity();

        mockMvc.perform(get(PERSONS_API))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRSTNAME)))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LASTNAME)))
                .andExpect(jsonPath("$.[*].registration").value(hasItem(DEFAULT_REGISTRATION)));
    }

    @Test
    public void testDeletePerson() throws Exception {
        Person person = createAndSavePersonEntity();
        int sizeBeforeAdding = personRepository.findAll().size();

        mockMvc.perform(delete(PERSONS_API + "/{id}", person.getId()))
                .andExpect(status().isOk());
        assertEquals(sizeBeforeAdding - 1, personRepository.findAll().size());
    }

    @Test
    void testUpdatePerson() throws Exception {
        Person person = createAndSavePersonEntity();
        PersonDto updatedPersonDto = PersonDto.builder().id(person.getId()).firstName(UPDATED_FIRSTNAME).build();

        mockMvc.perform(put(PERSONS_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updatedPersonDto)))
                .andExpect(status().isOk())
                .andReturn();

        Person updatedPerson = personRepository.findById(person.getId()).get();
        assertEquals(updatedPerson.getFirstName(), UPDATED_FIRSTNAME);
    }
}
