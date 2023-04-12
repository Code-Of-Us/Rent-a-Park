package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.models.Person;

public class PersonStub {

    public static Person givenPersonStub() {
        return givenPersonStubBuilder().build();
    }

    public static Person.PersonBuilder givenPersonStubBuilder() {
        return Person.builder().firstName("FirstName").lastName("LastName").registration("ZG-1234-AB");
    }
}
