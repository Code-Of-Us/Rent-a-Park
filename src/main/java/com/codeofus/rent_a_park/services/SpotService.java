package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SpotService {

    SpotRepository spotRepository;
    PersonRepository personRepository;
    PersonService personService;

    public List<Spot> getAllSpots(Pageable pageable) {
        Page<Spot> pagedResult = spotRepository.findAll(pageable);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return List.of();
        }
    }

    @Transactional
    public void deleteSpot(Integer id) {
        spotRepository.deleteById(id);
    }

    @Transactional
    public void reserveSpot(int id, int parkerId) {
        if (!spotRepository.findById(id).isPresent()) {
            throw new NoSuchElementException();
        }
        Spot spot = spotRepository.findById(id).stream().findFirst().get();
        if (!personRepository.findById(parkerId).isPresent()) {
            throw new NoSuchElementException();
        }
        Person parker = personRepository.findById(parkerId).stream().findFirst().get();
        spot.setParker(parker);
        spotRepository.save(spot);
        personService.reserveParkingSpot(spot, parker);
    }

    @Transactional
    public void rentASpot(Spot spot, int renterId) {
        if (!personRepository.findById(renterId).isPresent()) {
            throw new NoSuchElementException();
        }
        Person renter = personRepository.findById(renterId).stream().findFirst().get();
        spot.setRenter(renter);
        spotRepository.save(spot);
        personService.addParkingSpot(spot, renter);
    }

    @Transactional
    public void cancelReservation(int id, int parkerId) {
        if (!spotRepository.findById(id).isPresent()) {
            throw new NoSuchElementException();
        }
        Spot spot = spotRepository.findById(id).stream().findFirst().get();
        spot.setParker(null);
        spotRepository.save(spot);
        if (!personRepository.findById(parkerId).isPresent()) {
            throw new NoSuchElementException();
        }
        Person parker = personRepository.findById(parkerId).stream().findFirst().get();
        personService.cancelReservation(spot, parker);
    }
}