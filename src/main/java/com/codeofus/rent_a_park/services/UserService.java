package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.models.User;
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
    public void addNewUser(User user) {
        if (!(userRepository.findAll()).contains(user)) {
            userRepository.save(user);
        }
    }

    @Transactional
    public void deleteUser(User user) {
        if ((userRepository.findAll()).contains(user)) {
            userRepository.delete(user);
        }
    }

    @Transactional
    public void reserveParkingSpot(Spot spot, User parker) {
        List<Spot> parkingSpots = parker.getParkingSpots(); //!!!!!
        parkingSpots.add(spot);
        if (userRepository.findAll().contains(parker)) {
            userRepository.delete(parker);
        }
        parker.setParkingSpots(parkingSpots); //!!!!!
        userRepository.save(parker);
    }

    @Transactional
    public void addParkingSpot(Spot spot, User renter) {
        List<Spot> rentedSpots = renter.getRentedSpots(); //!!!!!
        rentedSpots.add(spot);
        if (userRepository.findAll().contains(renter)) {
            userRepository.delete(renter);
        }
        renter.setRentedSpots(rentedSpots); //!!!!!
        userRepository.save(renter);
    }

    @Transactional
    public void cancelReservation(Spot spot, User parker) {
        List<Spot> parkingSpots = parker.getParkingSpots(); //!!!!!
        parkingSpots.remove(spot);
        userRepository.delete(parker);
        parker.setParkingSpots(parkingSpots); //!!!!!
        userRepository.save(parker);
    }

}
