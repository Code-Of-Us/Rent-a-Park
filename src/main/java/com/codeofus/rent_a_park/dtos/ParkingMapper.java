package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
    PersonDto personToDto(Person person);

    @Mapping(target = "renter.rentedSpots", ignore = true)
    @Mapping(target = "renter.parkingSpots", ignore = true)
    @Mapping(target = "parker.rentedSpots", ignore = true)
    @Mapping(target = "parker.parkingSpots", ignore = true)
    SpotDto spotToDto(Spot spot);

    List<SpotDto> spotsToDtoList(List<Spot> spots);

    Person toPerson(PersonDto person);

    Spot toSpot(SpotDto spot);

}