//package com.codeofus.rent_a_park.services;
//
//import com.codeofus.rent_a_park.models.Person;
//import com.codeofus.rent_a_park.models.Spot;
//import com.codeofus.rent_a_park.repositories.PersonRepository;
//import com.codeofus.rent_a_park.repositories.SpotRepository;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//@Service
//public class SpotService {
//
//    SpotRepository spotRepository;
//    PersonRepository personRepository;
//    PersonService personService;
//
//    public List<Spot> getAllSpots(Pageable pageable) {
//        Page<Spot> pagedResult = spotRepository.findAll(pageable);
//        return pagedResult.getContent();
//    }
//
//    @Transactional
//    public void deleteSpot(Integer id) {
//        spotRepository.deleteById(id);
//    }
//
//    @Transactional
//    public void reserveSpot(int id, int parkerId) {
//        Person parker = personRepository.findById(parkerId).orElseThrow(() -> {
//            throw new NoSuchElementException(String.format("Person with id [%d] not found", parkerId));
//        });
//        Spot spot = spotRepository.findById(id).orElseThrow(() -> {
//            throw new NoSuchElementException(String.format("Spot with id [%d] not found", id));
//        });
//        spot.setParker(parker);
//    }
//
//    @Transactional
//    public Spot addNewParkingSpot(Spot spot) {
//        return spotRepository.save(spot);
//    }
//
//    @Transactional
//    public void cancelReservation(int id, int parkerId) {
//        Spot spot = spotRepository.findById(id).orElseThrow(() -> {
//            throw new NoSuchElementException(String.format("Spot with id [%d] not found", id));
//        });
//        if (spot.getParker().getId() != parkerId) {
//            throw new RuntimeException(String.format("Parker [%d] has not reservation on the spot [%d]", parkerId, id));
//        }
//        spot.setParker(null);
//    }
//}