package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    private PersonRepository personRepository;

    private ParkingMapper mapper;

    @Cacheable(value = "persons")
    public List<PersonDto> getAll() {
        return personRepository.findAll().stream().map(mapper::personToDto).toList();
    }

    @Transactional
    public Person addNewPerson(PersonDto personDto) {
        return personRepository.save(mapper.toPerson(personDto));
    }

    @Transactional
    @CachePut(cacheNames = "person", key = "#person.id")
    public Optional<PersonDto> updatePerson(PersonDto person) {
        return Optional
                .of(personRepository.findById(person.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(
                        user -> {
                            user.setFirstName(person.getFirstName());
                            user.setLastName(person.getLastName());
                            user.setRegistration(person.getRegistration());
                            user = personRepository.save(user);
                            return user;
                        }
                )
                .map(mapper::personToDto);
    }

    @Transactional
    @CacheEvict(value = "student", key = "#personId")
    public void deletePerson(Integer personId) {
        personRepository
                .findOneById(personId)
                .ifPresent(
                        personRepository::delete
                );
    }

    @Transactional
    public void reserveParkingSpot(Spot spot, Person parker) {
        List<Spot> parkingSpots = parker.getParkingSpots();
        parkingSpots.add(spot);
        if (personRepository.findAll().contains(parker)) {
            personRepository.delete(parker);
        }
        parker.setParkingSpots(parkingSpots);
        personRepository.save(parker);
    }

    @Transactional
    public void addParkingSpot(Spot spot, Person renter) {
        List<Spot> rentedSpots = renter.getRentedSpots();
        rentedSpots.add(spot);
        if (personRepository.findAll().contains(renter)) {
            personRepository.delete(renter);
        }
        renter.setRentedSpots(rentedSpots);
        personRepository.save(renter);
    }

    @Transactional
    public void cancelReservation(Spot spot, Person parker) {
        List<Spot> parkingSpots = parker.getParkingSpots();
        parkingSpots.remove(spot);
        personRepository.delete(parker);
        parker.setParkingSpots(parkingSpots);
        personRepository.save(parker);
    }

}
