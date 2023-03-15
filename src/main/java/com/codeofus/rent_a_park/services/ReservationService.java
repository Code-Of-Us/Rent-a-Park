package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.models.Reservation;
import com.codeofus.rent_a_park.repositories.ReservationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    public List<Reservation> getAll(Pageable pageable) {
        return reservationRepository.findAll(pageable).getContent();
    }

    public Optional<Reservation> getReservation(Long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> updateReservation(Reservation reservation) {
        Optional<Reservation> reservationToUpdate = reservationRepository.findById(reservation.getId());
        return reservationToUpdate.map(r -> r.updateReservation(reservation));
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}