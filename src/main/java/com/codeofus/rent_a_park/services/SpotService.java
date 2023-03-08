package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.models.Driver;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class SpotService {

    private SpotRepository spotRepository;
    private UserService userService;

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
    public void reserveSpot(Spot spot, Driver parker) {
        spot.setParker(parker); //!!!!!
        spotRepository.save(spot);
        userService.reserveParkingSpot(spot, parker);
    }

    @Transactional
    public void rentASpot(Spot spot, Driver renter) {
        spot.setRenter(renter); //!!!!!
        spotRepository.save(spot);
        userService.addParkingSpot(spot, renter);
    }

    @Transactional
    public void cancelReservation(Spot spot, Driver parker) {
        if (spotRepository.findAll().contains(spot)) {
            spot.setParker(null); //!!!!!
            userService.cancelReservation(spot, parker);
        }
    }
}
