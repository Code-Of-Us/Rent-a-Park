package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.models.Spot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SpotMapper {

    SpotDto spotToSpotDTO(Spot spot);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "parkingZone", source = "parkingZone")
    @Mapping(target = "renter", source = "renter")
    Spot spotDTOtoSpot(SpotDto spotDto);
}
