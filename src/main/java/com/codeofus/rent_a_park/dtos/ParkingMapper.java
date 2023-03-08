package com.codeofus.rent_a_park.dtos;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.models.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
    @Mapping(source = "firstName", target = "driver.firstName")
    @Mapping(source = "lastName", target = "driver.lastName")
    @Mapping(source = "registration", target = "driver.registration")
    @Mapping(source = "rentedSpots", target = "driver.rentedSpots")
    @Mapping(source = "parkingSpots", target = "driver.parkingSpots")
    DriverDto driverToDto(Driver driver);

    @Mapping(source = "address", target = "spot.address")
    @Mapping(source = "capacity", target = "spot.capacity")
    @Mapping(source = "availability", target = "spot.availability")
    @Mapping(source = "renter", target = "spot.renter")
    @Mapping(source = "parker", target = "spot.parker")
    SpotDto spotToDto(Spot spot);

    @Mapping(source = "firstName", target = "driver.firstName")
    @Mapping(source = "lastName", target = "driver.lastName")
    @Mapping(source = "registration", target = "driver.registration")
    @Mapping(source = "rentedSpots", target = "driver.rentedSpots")
    @Mapping(source = "parkingSpots", target = "driver.parkingSpots")
    Driver toDriver(DriverDto driver);

    @Mapping(source = "address", target = "spot.address")
    @Mapping(source = "capacity", target = "spot.capacity")
    @Mapping(source = "availability", target = "spot.availability")
    @Mapping(source = "renter", target = "spot.renter")
    @Mapping(source = "parker", target = "spot.parker")
    Spot toSpot(SpotDto spot);
}
