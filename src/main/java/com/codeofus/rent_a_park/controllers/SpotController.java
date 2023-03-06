package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.dtos.UserDto;
import com.codeofus.rent_a_park.services.SpotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "parking-reservation")
public class SpotController {

    SpotService spotService;
    ParkingMapper parkingMapper;

    @PostMapping(path = "/addParking")
    private void addNewParkingSpot(@RequestBody SpotDto spot, @RequestBody UserDto renter){
        spotService.rentASpot(parkingMapper.toSpot(spot), parkingMapper.toUser(renter));
    }

    @PostMapping
    private void reserveParkingSpot(@RequestBody SpotDto spot, @RequestBody UserDto parker) {
        spotService.reserveSpot(parkingMapper.toSpot(spot), parkingMapper.toUser(parker));
    }

    @DeleteMapping
    private void deleteParkingSpot(@RequestBody SpotDto spot){
        spotService.deleteSpot(parkingMapper.toSpot(spot));
    }

    @DeleteMapping
    private void cancelReservation(@RequestBody SpotDto spot, @RequestBody UserDto parker){
        spotService.cancelReservation(parkingMapper.toSpot(spot), parkingMapper.toUser(parker));
    }

    @GetMapping(path = "/parking-spots")
    private List<SpotDto> getAllParkingSpots() {
        return spotService
                .getAllSpots()
                .stream()
                .map(parkingMapper::spotToDto)
                .collect(Collectors.toList());
    }

}
