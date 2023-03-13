package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    ParkingMapper mapper;

    @PostMapping
    public PersonDto addPerson(@RequestBody PersonDto personDto) {
        Person createdPerson = personService.addNewPerson(mapper.toPerson(personDto));
        return mapper.personToDto(createdPerson);
    }

    @PutMapping
    public Optional<Person> updatePerson(@RequestBody PersonDto personDto) {
        return personService.updatePerson(mapper.toPerson(personDto));
    }

    @GetMapping
    public List<PersonDto> getAllPersons(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return personService.getAllPersons(pageable).stream().map(mapper::personToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Integer id) {
        personService.deletePerson(id);
    }

}