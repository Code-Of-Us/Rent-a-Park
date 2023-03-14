package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Transactional
    public Person addNewPerson(Person person) {
        return personRepository.save(person);
    }

    @Cacheable(value = "persons", key = "#pageable")
    public List<Person> getAllPersons(Pageable pageable) {
        Page<Person> pagedResult = personRepository.findAll(pageable);
        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return List.of();
        }
    }

    @Transactional
    @CachePut(cacheNames = "person", key = "#person.id")
    public Optional<Person> updatePerson(Person person) {
        Optional<Person> personToUpdate = personRepository.findById(person.getId());
        return personToUpdate.map(p -> p.UpdatePerson(person));
    }

    @Transactional
    @CacheEvict(value = "student", key = "#personId")
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