package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.dtos.PersonInfo;
import com.codeofus.rent_a_park.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDto personToPersonDTO(Person person);

    Person personDTOtoPerson(PersonDto personDto);

    PersonDto personInfoToPersonDto(PersonInfo personInfo);

    PersonInfo personToPersonInfo(Person person);

    List<PersonInfo> personListToPersonInfoList(List<Person> personList);

}
