package com.codeofus.rent_a_park.repositories;

import com.codeofus.rent_a_park.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Driver, Integer> {

    Driver getUserByRegistration(String registration);

}
