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

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "registration", source = "registration")
    Person personDTOtoPerson(PersonDto personDto);

    List<PersonDto> personToPersonDTO(List<Person> persons);

    List<Person> personDTOtoPerson(List<PersonDto> personDtos);

    PersonDto personInfoToPersonDto(PersonInfo personInfo);

    List<PersonDto> personInfoListToPersonDtoList(List<PersonInfo> personInfo);

    PersonInfo personToPersonInfo(Person person);

    List<PersonInfo> personListToPersonInfoList(List<Person> personList);

}
