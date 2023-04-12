package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.models.Spot;

public class SpotStub {

    public static Spot givenSpotStub() {
        return givenSpotStubBuilder().build();
    }

    public static Spot.SpotBuilder givenSpotStubBuilder() {
        return Spot.builder().renter(PersonStub.givenPersonStub()).address("Zagrebacka 1").parkingZone("4.2");
    }

}
