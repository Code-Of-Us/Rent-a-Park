package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.dtos.SpotDTO;

public class SpotDTOStub {

    public static SpotDTO givenSpotDtoStub() {
        return givenSpotDtoStubBuilder().build();
    }

    public static SpotDTO.SpotDTOBuilder givenSpotDtoStubBuilder() {
        return SpotDTO.builder().renter(PersonDTOStub.givenPersonDtoStub()).address("Zagrebacka 1").parkingZone("4.2");
    }

}
