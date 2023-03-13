package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingMapper {

    PersonDto personToDto(Person person);

    SpotDto spotToDto(Spot spot);

    List<SpotDto> spotsToDtoList(List<Spot> spots);

    Person toPerson(PersonDto driver);

    Spot toSpot(SpotDto spot);

}