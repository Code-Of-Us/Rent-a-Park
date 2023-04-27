package com.codeofus.rent_a_park.kafka;

import com.codeofus.reservations.ReservationDto;

public class ReservationDtoStub {
    public static ReservationDto createReservationSub() {
        return createReservationStubBuilder().build();
    }

    public static ReservationDto.Builder createReservationStubBuilder() {
        return ReservationDto.newBuilder()
                .setPersonId(1)
                .setSpotId(1)
                .setPersonEmail("mail@gmail.com")
                .setSpotAddress("address")
                .setCreatedAt(null)
                .setReservedFrom(null)
                .setReservedUntil(null);
    }
}
