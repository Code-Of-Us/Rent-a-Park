package com.codeofus.rent_a_park.stubs;

import com.codeofus.rent_a_park.dtos.PersonDTO;

public class PersonDTOStub {

    public static PersonDTO givenPersonDtoStub() {
        return givenPersonDtoStubBuilder().build();
    }

    public static PersonDTO.PersonDTOBuilder givenPersonDtoStubBuilder() {
        return PersonDTO.builder().firstName("FirstName").lastName("LastName").registration("ZG-1234-AB");
    }

}
