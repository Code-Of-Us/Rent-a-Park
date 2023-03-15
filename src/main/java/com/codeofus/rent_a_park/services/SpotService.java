package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.repositories.SpotRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SpotService {

    SpotRepository spotRepository;

    public List<Spot> getAll(Pageable pageable) {
        Page<Spot> pagedResult = spotRepository.findAll(pageable);
        return pagedResult.getContent();
    }

    public Optional<Spot> getSpot(long id) {
        return spotRepository.findById(id);
    }

    public Spot createSpot(Spot spot) {
        return spotRepository.save(spot);
    }

    @Transactional
    public Optional<Spot> updateSpot(Spot spot) {
        Optional<Spot> spotToUpdate = spotRepository.findById(spot.getId());
        return spotToUpdate.map(r -> r.updateSpot(spot));
    }


    @Transactional
    public void deleteSpot(Long id) {
        spotRepository.deleteById(id);
    }
}