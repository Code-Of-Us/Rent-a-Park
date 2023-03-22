package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
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

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PersonService {

    PersonRepository personRepository;

    @Transactional
    public Person addNewPerson(Person person) {
        return personRepository.save(person);
    }

    @Cacheable(value = "persons")
    public List<Person> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        persons.forEach(p -> {
            Hibernate.initialize(p.getParkingSpots());
            Hibernate.initialize(p.getRentedSpots());
        });
        return persons;
    }

    @Cacheable(value = "persons", key = "#pageable")
    public List<Person> getAllPersons(Pageable pageable) {
        Page<Person> pagedResult = personRepository.findAll(pageable);
        pagedResult.forEach(p -> {
            Hibernate.initialize(p.getParkingSpots());
            Hibernate.initialize(p.getRentedSpots());
        });
        return pagedResult.getContent();
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

    @Transactional
    public void reserveParkingSpot(Spot spot, Person parker) {
        List<Spot> parkingSpots = parker.getParkingSpots();
        parkingSpots.add(spot);
        if (personRepository.findById(parker.getId()).isPresent()) {
            personRepository.delete(parker);
        }
        parker.setParkingSpots(parkingSpots);
        personRepository.save(parker);
    }

    @Transactional
    public void addParkingSpot(Spot spot, Person renter) {
        List<Spot> rentedSpots = renter.getRentedSpots();
        rentedSpots.add(spot);
        renter.setRentedSpots(rentedSpots);
        personRepository.save(renter);
    }

    @Transactional
    public void cancelReservation(Spot spot, Person parker) {
        List<Spot> parkingSpots = parker.getParkingSpots();
        parkingSpots.remove(spot);
        parker.setParkingSpots(parkingSpots);
        personRepository.save(parker);
    }

}