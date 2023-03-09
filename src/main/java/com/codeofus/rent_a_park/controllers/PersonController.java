package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.errors.BadRequestException;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.services.PersonService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class PersonController {

    private PersonService personService;

    @PostMapping()
    public ResponseEntity<Person> addPerson(@RequestBody PersonDto personDto) throws BadRequestException, URISyntaxException {
        if (personDto.getId() != null) {
            throw new BadRequestException("A new user cannot already have an ID", "users", "idexists");
        }
        Person newUser = personService.addNewPerson(personDto);
        return ResponseEntity
                .created(new URI("/api/v1/users/" + newUser.getId()))
                .body(newUser);
    }

    @PutMapping
    public ResponseEntity<PersonDto> updatePerson(@RequestBody PersonDto personDto) {
        Optional<PersonDto> updatedUser = personService.updatePerson(personDto);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<PersonDto> getAllPersons() {
        return personService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        personService.deletePerson(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
