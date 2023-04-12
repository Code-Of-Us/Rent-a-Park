package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.PersonDTO;
import com.codeofus.rent_a_park.errors.BadEntityException;
import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/persons")
public class PersonController {

    PersonService personService;
    PersonMapper personMapper;

    @GetMapping
    public Page<PersonDTO> getAllPersons(Pageable pageable) {
        return personService.getAllPersons(pageable).map(personMapper::personToPersonDTO);
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable int id) throws BadEntityException {
        Person person = personService.getPerson(id);
        return personMapper.personToPersonDTO(person);
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO personDto) throws BadEntityException {
        Person createdPerson = personService.createPerson(personMapper.personDTOtoPerson(personDto));
        return personMapper.personToPersonDTO(createdPerson);
    }

    @PutMapping
    public PersonDTO updatePerson(@RequestBody PersonDTO personDto) {
        Person updatedPerson = personService.updatePerson(personMapper.personDTOtoPerson(personDto));
        return personMapper.personToPersonDTO(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }

}