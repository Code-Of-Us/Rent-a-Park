package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.dtos.CreateReservationDTO;

public class CreateReservationDTOStub {

    public static CreateReservationDTO givenCreateReservationDTOStub() {
        return givenCreateReservationDTOStubBuilder().build();
    }

    public static CreateReservationDTO.CreateReservationDTOBuilder givenCreateReservationDTOStubBuilder() {
        return CreateReservationDTO.builder().personId(1).spotId(1);
    }

}
