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

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class PersonService {

    private PersonRepository personRepository;

    @Transactional
    public void addNewPerson(Person person) {
        if (!(personRepository.findAll()).contains(person)) {
            personRepository.save(person);
        }
    }

    @Transactional
    public void deletePerson(Person person) {
        if ((personRepository.findAll()).contains(person)) {
            personRepository.delete(person);
        }
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
