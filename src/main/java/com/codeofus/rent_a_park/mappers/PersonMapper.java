package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto personToPersonDTO(Person person);

    Person personDTOtoPerson(PersonDto personDto);

    List<PersonDto> personToPersonDTO(List<Person> persons);

    List<Person> personDTOtoPerson(List<PersonDto> personDtos);

}
