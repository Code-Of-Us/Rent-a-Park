package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class PersonController {

    private PersonService personService;
    private ParkingMapper mapper;

    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody PersonDto personDto) {
        Person createdPerson = personService.addNewPerson(mapper.toPerson(personDto));
        return ResponseEntity.ok(mapper.personToDto(createdPerson));
    }

    @GetMapping("/all")
    public List<Person> getAllPersons() {
        return personService.getAll();
    }

    @DeleteMapping("/delete")
    public void deletePerson(@RequestBody PersonDto personDto) {
        personService.deletePerson(mapper.toPerson(personDto));
    }
}
