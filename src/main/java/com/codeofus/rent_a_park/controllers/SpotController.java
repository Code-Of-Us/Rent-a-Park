package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.services.SpotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/parking")
public class SpotController {

    SpotService spotService;
    ParkingMapper mapper;

    @PostMapping
    public SpotDto addNewParkingSpot(@RequestBody SpotDto spotDto) {
        Spot spot = spotService.addNewParkingSpot(mapper.toSpot(spotDto));
        return mapper.spotToDto(spot);
    }

    @PutMapping("/{id}/reserve")
    public void reserveParkingSpot(@PathVariable int id, @RequestBody int parkerId) {
        spotService.reserveSpot(id, parkerId);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable int id) {
        spotService.deleteSpot(id);
    }

    @PutMapping("/{id}/cancel")
    public void cancelReservation(@PathVariable int id, @RequestBody int parkerId) {
        spotService.cancelReservation(id, parkerId);
    }

    @GetMapping
    public List<SpotDto> getAllParkingSpots(Pageable pageable) {
        return spotService.getAllSpots(pageable).stream().map(mapper::spotToDto).collect(Collectors.toList());
    }

}