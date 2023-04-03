package com.codeofus.rent_a_park.controllers;

import com.codeofus.rent_a_park.dtos.CreateReservationDto;
import com.codeofus.rent_a_park.dtos.ReservationDto;
import com.codeofus.rent_a_park.errors.BadEntityException;
import com.codeofus.rent_a_park.mappers.ReservationMapper;
import com.codeofus.rent_a_park.models.Reservation;
import com.codeofus.rent_a_park.services.ReservationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/reservations")
public class ReservationController {

    ReservationService reservationService;

    ReservationMapper reservationMapper;

    @GetMapping
    public Page<ReservationDto> getAllReservations(Pageable pageable) {
        return reservationService.getAllReservations(pageable).map(reservationMapper::reservationToReservationDTO);
    }

    @GetMapping("/{id}")
    public ReservationDto getReservation(@PathVariable int id) throws BadEntityException {
        Reservation reservation = reservationService.getReservation(id);
        return reservationMapper.reservationToReservationDTO(reservation);
    }

    @PostMapping
    public ReservationDto createReservation(@RequestBody CreateReservationDto reservationDto) throws BadEntityException {
        Reservation createdReservation = reservationService.createReservation(reservationMapper.createOrUpdateDTOtoReservation(reservationDto));
        return reservationMapper.reservationToReservationDTO(createdReservation);
    }

    @PutMapping
    public ReservationDto updateReservation(@RequestBody ReservationDto reservationDto) throws BadEntityException {
        Reservation updatedReservation = reservationService.updateReservation(reservationMapper.reservationDTOtoReservation(reservationDto));
        return reservationMapper.reservationToReservationDTO(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservationService.deleteReservation(id);
    }
}
