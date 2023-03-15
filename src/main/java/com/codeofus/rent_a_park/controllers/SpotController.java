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

    @PostMapping("/{id}/reserve/{parkerId}")
    public void reserveParkingSpot(@PathVariable int id, @PathVariable int parkerId) {
        spotService.reserveSpot(id, parkerId);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable int id) {
        spotService.deleteSpot(id);
    }

    @PostMapping("/{id}/cancel/{parkerId}")
    public void cancelReservation(@PathVariable int id, @PathVariable int parkerId) {
        spotService.cancelReservation(id, parkerId);
    }

    @GetMapping
    public List<SpotDto> getAllParkingSpots(Pageable pageable) {
        List<Spot> spots = spotService.getAllSpots(pageable);
        return mapper.spotsToDtoList(spots);
    }

}