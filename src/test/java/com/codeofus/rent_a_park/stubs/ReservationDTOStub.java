package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.dtos.ReservationDTO;

public class ReservationDTOStub {

    public static ReservationDTO givenReservationDtoStub() {
        return givenReservationDtoStubBuilder().build();
    }

    public static ReservationDTO.ReservationDTOBuilder givenReservationDtoStubBuilder() {
        return ReservationDTO.builder().person(PersonDTOStub.givenPersonDtoStub()).spot(SpotDTOStub.givenSpotDtoStub());
    }

}
