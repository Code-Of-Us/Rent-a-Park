package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SpotService {

    SpotRepository spotRepository;
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
    public void reserveSpot(int id, Person parker) {
        Spot spot = spotRepository.getSpotById(id);
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
    public void cancelReservation(int id, Person parker) {
        Spot spot = spotRepository.getSpotById(id);
        spot.setParker(null);
        spotRepository.save(spot);
        personService.cancelReservation(spot, parker);
    }
}