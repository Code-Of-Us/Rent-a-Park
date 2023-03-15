package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.SpotDto;
import com.codeofus.rent_a_park.models.Spot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpotMapper {

    SpotDto spotToSpotDTO(Spot spot);

    Spot spotDTOtoSpot(SpotDto spotDto);
}
