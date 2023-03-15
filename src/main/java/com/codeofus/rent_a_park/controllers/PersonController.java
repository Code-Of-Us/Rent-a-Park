package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.errors.BadRequestException;
import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/persons")
public class PersonController {

    PersonService personService;
    PersonMapper mapper;

    @PostMapping
    public PersonDto addPerson(@RequestBody PersonDto personDto) throws BadRequestException {
        if (personDto.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID", "users", "idexists");
        }
        Person createdPerson = personService.createPerson(mapper.personDTOtoPerson(personDto));
        return mapper.personToPersonDTO(createdPerson);
    }

    @PutMapping
    public Optional<Person> updatePerson(@RequestBody PersonDto personDto) {
        return personService.updatePerson(mapper.personDTOtoPerson(personDto));
    }

    @GetMapping
    public List<PersonDto> getAllPersons(Pageable pageable) {
        return personService.getAllPersons(pageable).stream().map(mapper::personToPersonDTO).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

}