package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/persons")
public class PersonController {

    PersonService personService;
    ParkingMapper mapper;

    @PostMapping
    public PersonDto addPerson(@RequestBody PersonDto personDto) {
        Person createdPerson = personService.addNewPerson(mapper.toPerson(personDto));
        return mapper.personToDto(createdPerson);
    }

    @PutMapping
    public PersonDto updatePerson(@RequestBody PersonDto personDto) {
        Person updatedPerson =  personService.updatePerson(mapper.toPerson(personDto));
        return mapper.personToDto(updatedPerson);
    }

    @GetMapping
    public List<PersonDto> getAllPersons(Pageable pageable) {
        return personService.getAllPersons(pageable).stream().map(mapper::personToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable int id) {
        personService.deletePerson(id);
    }

}