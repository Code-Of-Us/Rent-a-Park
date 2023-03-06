package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "registration", target = "user.registration")
    @Mapping(source = "rentedSpots", target = "user.rentedSpots")
    @Mapping(source = "parkingSpots", target = "user.parkingSpots")
    UserDto userToDto(User user);

    @Mapping(source = "address", target = "spot.address")
    @Mapping(source = "capacity", target = "spot.capacity")
    @Mapping(source = "availability", target = "spot.availability")
    @Mapping(source = "renter", target = "spot.renter")
    @Mapping(source = "parker", target = "spot.parker")
    SpotDto spotToDto(Spot spot);

    @Mapping(source = "firstName", target = "user.firstName")
    @Mapping(source = "lastName", target = "user.lastName")
    @Mapping(source = "registration", target = "user.registration")
    @Mapping(source = "rentedSpots", target = "user.rentedSpots")
    @Mapping(source = "parkingSpots", target = "user.parkingSpots")
    User toUser(UserDto user);

    @Mapping(source = "address", target = "spot.address")
    @Mapping(source = "capacity", target = "spot.capacity")
    @Mapping(source = "availability", target = "spot.availability")
    @Mapping(source = "renter", target = "spot.renter")
    @Mapping(source = "parker", target = "spot.parker")
    Spot toSpot(SpotDto spot);
}
