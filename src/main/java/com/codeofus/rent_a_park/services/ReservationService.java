package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.errors.BadEntityException;
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

    public List<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable).getContent();
    }

    public Reservation getReservation(int id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new BadEntityException("Reservation does not exist", "reservations", "does-not-exist"));
    }

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation updateReservation(Reservation reservation) {
        Optional<Reservation> reservationToUpdate = reservationRepository.findById(reservation.getId());
        if (reservationToUpdate.isEmpty()) {
            throw new BadEntityException("Reservation does not exist", "reservations", "id-does-not-exist");
        }
        return reservationToUpdate.get().updateReservation(reservation);
    }

    @Transactional
    public void deleteReservation(int reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
