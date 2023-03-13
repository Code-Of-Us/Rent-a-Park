package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
    
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Transactional
    public Person addNewPerson(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public Optional<Person> updatePerson(Person person) {
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
                );
    }

    @Transactional
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
        if (personRepository.findById(renter.getId()).isPresent()) {
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