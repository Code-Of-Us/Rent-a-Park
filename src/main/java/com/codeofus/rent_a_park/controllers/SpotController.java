package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.mappers.SpotMapper;
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
    SpotMapper mapper;

    @PostMapping
    public SpotDto addNewParkingSpot(@RequestBody SpotDto spotDto) {
        Spot spot = spotService.addNewParkingSpot(mapper.spotDTOtoSpot(spotDto));
        return mapper.spotToSpotDTO(spot);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable Long id) {
        spotService.deleteSpot(id);
    }

    @GetMapping
    public List<SpotDto> getAllParkingSpots(Pageable pageable) {
        return spotService.getAllSpots(pageable).stream().map(mapper::spotToSpotDTO).collect(Collectors.toList());
    }

}