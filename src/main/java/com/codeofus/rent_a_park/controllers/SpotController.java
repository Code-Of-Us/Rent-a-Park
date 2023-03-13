package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.SpotDto;
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
@RequestMapping(path = "/api/v1/parking")
public class SpotController {

    SpotService spotService;
    ParkingMapper parkingMapper;

    @PostMapping
    private void addNewParkingSpot(@RequestBody SpotDto spot, @RequestBody PersonDto renter) {
        spotService.rentASpot(parkingMapper.toSpot(spot), parkingMapper.toPerson(renter));
    }

    @PutMapping("/{id}/reserve")
    private void reserveParkingSpot(@PathVariable int id, @RequestBody PersonDto parker) {
        spotService.reserveSpot(id, parkingMapper.toPerson(parker));
    }

    @DeleteMapping("/{id}")
    private void deleteParkingSpot(@PathVariable int id) {
        spotService.deleteSpot(id);
    }

    @PutMapping("/{id}/cancel")
    private void cancelReservation(@PathVariable int id, @RequestBody PersonDto parker) {
        spotService.cancelReservation(id, parkingMapper.toPerson(parker));
    }

    @GetMapping
    private List<SpotDto> getAllParkingSpots(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
        return spotService.getAllSpots(pageNo, pageSize, sortBy).stream().map(parkingMapper::spotToDto).collect(Collectors.toList());
    }

}