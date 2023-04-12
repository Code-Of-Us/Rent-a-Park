package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.dtos.PersonDTO;
import com.codeofus.rent_a_park.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonDTO personToPersonDTO(Person person);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "registration", source = "registration")
    Person personDTOtoPerson(PersonDTO personDto);

    List<PersonDTO> personListToPersonDTOList(List<Person> personList);

}
