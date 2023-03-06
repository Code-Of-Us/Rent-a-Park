package com.codeofus.rent_a_park.repositories;

import com.codeofus.rent_a_park.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserByRegistration(String registration);

}
