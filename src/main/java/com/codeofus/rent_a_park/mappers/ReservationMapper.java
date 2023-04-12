package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.CreateReservationDto;
import com.codeofus.rent_a_park.dtos.ReservationDto;
import com.codeofus.rent_a_park.models.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationDto reservationToReservationDTO(Reservation reservation);

    Reservation reservationDTOtoReservation(ReservationDto reservationDto);

    @Mapping(source = "personId", target = "person.id")
    @Mapping(source = "spotId", target = "spot.id")
    Reservation createOrUpdateDTOtoReservation(CreateReservationDto reservation);

}
