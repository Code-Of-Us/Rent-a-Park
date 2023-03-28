package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.PersonInfo;
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

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/persons")
public class PersonController {

    PersonService personService;
    PersonMapper personMapper;

    @GetMapping
    public List<PersonDto> getAllPersons(Pageable pageable) {
        List<PersonInfo> personInfos = personService.getAllPersons(pageable);
        return personMapper.personInfoListToPersonDtoList(personInfos);
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable int id) throws BadRequestException {
        Optional<Person> person = personService.getPerson(id);
        if (person.isPresent()) {
            return personMapper.personToPersonDTO(person.get());
        } else {
            throw new BadRequestException("Person does not exist", "persons", "does-not-exist");
        }
    }

    @PostMapping
    public PersonDto createPerson(@RequestBody PersonDto personDto) throws BadRequestException {
        if (personDto.getId() != null) {
            throw new BadRequestException("A new person cannot already have an ID", "persons", "id-exists");
        }
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