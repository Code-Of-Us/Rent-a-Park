package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
    @Mapping(source = "firstName", target = "person.firstName")
    @Mapping(source = "lastName", target = "person.lastName")
    @Mapping(source = "registration", target = "person.registration")
    @Mapping(source = "rentedSpots", target = "person.rentedSpots")
    @Mapping(source = "parkingSpots", target = "person.parkingSpots")
    PersonDto personToDto(Person person);

    @Mapping(source = "address", target = "spot.address")
    @Mapping(source = "capacity", target = "spot.capacity")
    @Mapping(source = "availability", target = "spot.availability")
    @Mapping(source = "renter", target = "spot.renter")
    @Mapping(source = "parker", target = "spot.parker")
    SpotDto spotToDto(Spot spot);

    @Mapping(source = "firstName", target = "person.firstName")
    @Mapping(source = "lastName", target = "person.lastName")
    @Mapping(source = "registration", target = "person.registration")
    @Mapping(source = "rentedSpots", target = "person.rentedSpots")
    @Mapping(source = "parkingSpots", target = "person.parkingSpots")
    Person toPerson(PersonDto driver);

    @Mapping(source = "address", target = "spot.address")
    @Mapping(source = "capacity", target = "spot.capacity")
    @Mapping(source = "availability", target = "spot.availability")
    @Mapping(source = "renter", target = "spot.renter")
    @Mapping(source = "parker", target = "spot.parker")
    Spot toSpot(SpotDto spot);
}
