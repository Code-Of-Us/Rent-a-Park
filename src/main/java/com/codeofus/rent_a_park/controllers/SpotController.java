package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.errors.BadRequestException;
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
    SpotMapper spotMapper;

    @GetMapping
    public List<SpotDto> getAll(Pageable pageable) {
        return spotService.getAll(pageable).stream().map(spotMapper::spotToSpotDTO).collect(Collectors.toList());
    }

    @GetMapping ("/{id}")
    public SpotDto getSpot(@PathVariable long id) throws BadRequestException {
        Spot spot = spotService.getSpot(id);
        if (spot != null) {
            return spotMapper.spotToSpotDTO(spot);
        } else {
            throw new BadRequestException("Spot does not exist", "spots", "does-not-exist");
        }
    }

    @PostMapping
    public SpotDto createSpot(@RequestBody SpotDto spotDto) {
        Spot spot = spotService.createSpot(spotMapper.spotDTOtoSpot(spotDto));
        return spotMapper.spotToSpotDTO(spot);
    }

    @PutMapping
    public SpotDto updateSpot(@RequestBody SpotDto spotDto) {
        Spot spot = spotService.updateSpot(spotMapper.spotDTOtoSpot(spotDto));
        return spotMapper.spotToSpotDTO(spot);
    }

    @DeleteMapping("/{id}")
    public void deleteParkingSpot(@PathVariable long id) {
        spotService.deleteSpot(id);
    }

}