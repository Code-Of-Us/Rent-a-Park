package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SpotService {
    private SpotRepository spotRepository;
    private PersonService personService;

    public List<Spot> getAllSpots() {
        return spotRepository.findAll();
    }

    @Transactional
    public void deleteSpot(Spot spot) {
        if ((spotRepository.findAll()).contains(spot)) {
            spotRepository.delete(spot);
        }
    }

    @Transactional
    public void reserveSpot(Spot spot, Person parker) {
        spot.setParker(parker);
        spotRepository.save(spot);
        personService.reserveParkingSpot(spot, parker);
    }

    @Transactional
    public void rentASpot(Spot spot, Person renter) {
        spot.setRenter(renter);
        spotRepository.save(spot);
        personService.addParkingSpot(spot, renter);
    }

    @Transactional
    public void cancelReservation(Spot spot, Person parker) {
        if (spotRepository.findAll().contains(spot)) {
            spot.setParker(null);
            personService.cancelReservation(spot, parker);
        }
    }
}
