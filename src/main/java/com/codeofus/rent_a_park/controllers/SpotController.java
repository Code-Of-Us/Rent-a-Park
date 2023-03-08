package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.dtos.PersonDto;
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
@RequestMapping(path = "parking")
public class SpotController {

    SpotService spotService;
    ParkingMapper parkingMapper;

    @PostMapping(path = "/add")
    private void addNewParkingSpot(@RequestBody SpotDto spot, @RequestBody PersonDto renter){
        spotService.rentASpot(parkingMapper.toSpot(spot), parkingMapper.toPerson(renter));
    }

    @PostMapping("/reserve")
    private void reserveParkingSpot(@RequestBody SpotDto spot, @RequestBody PersonDto parker) {
        spotService.reserveSpot(parkingMapper.toSpot(spot), parkingMapper.toPerson(parker));
    }

    @DeleteMapping("/delete")
    private void deleteParkingSpot(@RequestBody SpotDto spot){
        spotService.deleteSpot(parkingMapper.toSpot(spot));
    }

    @PutMapping("/cancel")
    private void cancelReservation(@RequestBody SpotDto spot, @RequestBody PersonDto parker){
        spotService.cancelReservation(parkingMapper.toSpot(spot), parkingMapper.toPerson(parker));
    }

    @GetMapping(path = "/all")
    private List<SpotDto> getAllParkingSpots() {
        return spotService
                .getAllSpots()
                .stream()
                .map(parkingMapper::spotToDto)
                .collect(Collectors.toList());
    }

}
