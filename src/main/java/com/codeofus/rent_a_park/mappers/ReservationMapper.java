package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.CreateReservationDTO;
import com.codeofus.rent_a_park.dtos.ReservationDTO;
import com.codeofus.rent_a_park.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDTO reservationToReservationDTO(Reservation reservation);

    Reservation reservationDTOtoReservation(ReservationDTO reservationDto);

    @Mapping(source = "personId", target = "person.id")
    @Mapping(source = "spotId", target = "spot.id")
    Reservation createOrUpdateDTOtoReservation(CreateReservationDTO reservation);

}
