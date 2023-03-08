package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.models.Driver;
import com.codeofus.rent_a_park.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    @Transactional
    public void addNewUser(Driver driver) {
        if (!(userRepository.findAll()).contains(driver)) {
            userRepository.save(driver);
        }
    }

    @Transactional
    public void deleteUser(Driver driver) {
        if ((userRepository.findAll()).contains(driver)) {
            userRepository.delete(driver);
        }
    }

    @Transactional
    public void reserveParkingSpot(Spot spot, Driver parker) {
        List<Spot> parkingSpots = parker.getParkingSpots(); //!!!!!
        parkingSpots.add(spot);
        if (userRepository.findAll().contains(parker)) {
            userRepository.delete(parker);
        }
        parker.setParkingSpots(parkingSpots); //!!!!!
        userRepository.save(parker);
    }

    @Transactional
    public void addParkingSpot(Spot spot, Driver renter) {
        List<Spot> rentedSpots = renter.getRentedSpots(); //!!!!!
        rentedSpots.add(spot);
        if (userRepository.findAll().contains(renter)) {
            userRepository.delete(renter);
        }
        renter.setRentedSpots(rentedSpots); //!!!!!
        userRepository.save(renter);
    }

    @Transactional
    public void cancelReservation(Spot spot, Driver parker) {
        List<Spot> parkingSpots = parker.getParkingSpots(); //!!!!!
        parkingSpots.remove(spot);
        userRepository.delete(parker);
        parker.setParkingSpots(parkingSpots); //!!!!!
        userRepository.save(parker);
    }

}
