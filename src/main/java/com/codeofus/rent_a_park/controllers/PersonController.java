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

    @GetMapping
    public List<PersonDto> getAllPersons(Pageable pageable) {
        return personService.getAllPersons(pageable).stream().map(mapper::personToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable long id) throws BadRequestException {
        Optional<Person> person = personService.getPerson(id);
        if (person.isPresent()) {
            return mapper.personToPersonDTO(person.get());
        } else {
            throw new BadRequestException("Person does not exist", "persons", "does-not-exist");
        }
    }

    @PostMapping
    public PersonDto createPerson(@RequestBody PersonDto personDto) throws BadRequestException {
        if (personDto.getId() != null) {
            throw new BadRequestException("A new person cannot already have an ID", "persons", "id-exists");
        }
        Person createdPerson = personService.createPerson(mapper.personDTOtoPerson(personDto));
        return mapper.personToPersonDTO(createdPerson);
    }

    @PutMapping
    public Optional<Person> updatePerson(@RequestBody PersonDto personDto) {
        return personService.updatePerson(mapper.personDTOtoPerson(personDto));
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

}