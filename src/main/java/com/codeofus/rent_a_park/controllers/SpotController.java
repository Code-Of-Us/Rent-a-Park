package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.SpotDTO;
import com.codeofus.rent_a_park.errors.BadEntityException;
import com.codeofus.rent_a_park.mappers.SpotMapper;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.services.SpotService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/spots")
public class SpotController {

    SpotService spotService;
    SpotMapper spotMapper;

    @GetMapping
    public Page<SpotDTO> getAllSpots(Pageable pageable) {
        return spotService.getAllSpots(pageable).map(spotMapper::spotToSpotDTO);
    }

    @GetMapping("/{id}")
    public SpotDTO getSpot(@PathVariable int id) throws BadEntityException {
        Spot spot = spotService.getSpot(id);
        if (spot != null) {
            return spotMapper.spotToSpotDTO(spot);
        } else {
            throw new BadEntityException("Spot does not exist", "spots", "does-not-exist");
        }
    }

    @PostMapping
    public SpotDTO createSpot(@RequestBody SpotDTO spotDto) {
        Spot spot = spotService.createSpot(spotMapper.spotDTOtoSpot(spotDto));
        return spotMapper.spotToSpotDTO(spot);
    }

    @PutMapping
    public SpotDTO updateSpot(@RequestBody SpotDTO spotDto) {
        Spot spot = spotService.updateSpot(spotMapper.spotDTOtoSpot(spotDto));
        return spotMapper.spotToSpotDTO(spot);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable int id) {
        spotService.deleteSpot(id);
    }

}