package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PersonService {

    PersonRepository personRepository;

    @Cacheable(value = "persons")
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Cacheable(value = "persons", key = "#pageable")
    public List<Person> getAllPersons(Pageable pageable) {
        Page<Person> pagedResult = personRepository.findAll(pageable);
        return pagedResult.getContent();
    }

    public Optional<Person> getPerson(long id) {
        return personRepository.findById(id);
    }

    @Transactional
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    @CachePut(cacheNames = "person", key = "#person.id")
    public Optional<Person> updatePerson(Person person) {
        Optional<Person> personToUpdate = personRepository.findById(person.getId());
        return personToUpdate.map(p -> p.UpdatePerson(person));
    }

    @Transactional
    @CacheEvict(value = "person", key = "#personId")
    public void deletePerson(Long personId) {
        personRepository.deleteById(personId);
    }

}