package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.dtos.PersonInfo;
import com.codeofus.rent_a_park.mappers.PersonMapper;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PersonService {

    PersonRepository personRepository;

    PersonMapper mapper;

    @Transactional
    public Person addNewPerson(Person person) {
        return personRepository.save(person);
    }

    @Cacheable(value = "persons")
    public List<PersonInfo> getAllPersons() {
        return mapper.personListToPersonInfoList(personRepository.findAll());
    }

    @Cacheable(value = "persons", key = "#pageable")
    public List<PersonInfo> getAllPersons(Pageable pageable) {
        Page<Person> pagedResult = personRepository.findAll(pageable);
        return mapper.personListToPersonInfoList(pagedResult.getContent());
    }

    public Optional<Person> getPerson(int id) {
        return personRepository.findById(id);
    }

    @Transactional
    @CacheEvict(value = "persons", allEntries = true)
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    @Caching(put = {@CachePut(cacheNames = "person", key = "#person.id")}, evict = {@CacheEvict(value = "persons", allEntries = true)})
    public Person updatePerson(Person person) {
        Person personToUpdate = personRepository.findById(person.getId()).orElseThrow(() -> {
            throw new NoSuchElementException(String.format("Person with id [%d] not found", person.getId()));
        });
        return personToUpdate.updatePerson(person);
    }

    @Transactional
    @Caching(evict = {@CacheEvict(value = "person", key = "#personId"), @CacheEvict(value = "persons", allEntries = true)})
    public void deletePerson(Integer personId) {
        personRepository.deleteById(personId);
    }

}