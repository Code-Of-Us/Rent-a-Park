package com.codeofus.rent_a_park.repositories;

import com.codeofus.rent_a_park.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person getPersonByRegistration(String registration);
    Optional<Person> findOneById(Integer id);

    Person getPersonById(Integer id);

}