package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.ReservationDto;
import com.codeofus.rent_a_park.models.Reservation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDto reservationToReservationDTO(Reservation reservation);

    Reservation reservationDTOtoReservation(ReservationDto reservationDto);

    List<ReservationDto> reservationToReservationDTO(List<Reservation> reservations);

    List<Reservation> reservationDTOtoReservation(List<ReservationDto> reservationDtoList);

}
