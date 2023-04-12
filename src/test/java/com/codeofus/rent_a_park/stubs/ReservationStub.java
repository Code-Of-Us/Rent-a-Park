package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.models.Reservation;

public class ReservationStub {

    public static Reservation givenReservationStub() {
        return givenReservationStubBuilder().build();
    }

    public static Reservation.ReservationBuilder givenReservationStubBuilder() {
        return Reservation.builder().person(PersonStub.givenPersonStub()).spot(SpotStub.givenSpotStub());
    }

}
