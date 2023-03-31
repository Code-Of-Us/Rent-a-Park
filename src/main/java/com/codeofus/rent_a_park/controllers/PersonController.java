package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.errors.BadEntityException;
import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/persons")
public class PersonController {

    PersonService personService;
    PersonMapper personMapper;

    @GetMapping
    public Page<PersonDto> getAllPersons(Pageable pageable) {
        return new PageImpl<>(personService.getAllPersons(pageable).stream().map(personMapper::personInfoToPersonDto).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable int id) throws BadEntityException {
        Person person = personService.getPerson(id);
        return personMapper.personToPersonDTO(person);
    }

    @PostMapping
    public PersonDto createPerson(@RequestBody PersonDto personDto) throws BadEntityException {
        Person createdPerson = personService.createPerson(personMapper.personDTOtoPerson(personDto));
        return personMapper.personToPersonDTO(createdPerson);
    }

    @PutMapping
    public PersonDto updatePerson(@RequestBody PersonDto personDto) {
        Person updatedPerson = personService.updatePerson(personMapper.personDTOtoPerson(personDto));
        return personMapper.personToPersonDTO(updatedPerson);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }

}