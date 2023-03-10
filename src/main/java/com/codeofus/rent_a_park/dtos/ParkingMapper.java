package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingMapper {

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "registration", target = "registration")
    @Mapping(source = "rentedSpots", target = "rentedSpots")
    @Mapping(source = "parkingSpots", target = "parkingSpots")
    PersonDto personToDto(Person person);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "availability", target = "availability")
    @Mapping(source = "renter", target = "renter")
    @Mapping(source = "parker", target = "parker")
    SpotDto spotToDto(Spot spot);

    List<SpotDto> spotsToDtoList(List<Spot> spots);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "registration", target = "registration")
    @Mapping(source = "rentedSpots", target = "rentedSpots")
    @Mapping(source = "parkingSpots", target = "parkingSpots")
    Person toPerson(PersonDto driver);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "availability", target = "availability")
    @Mapping(source = "renter", target = "renter")
    @Mapping(source = "parker", target = "parker")
    Spot toSpot(SpotDto spot);

}