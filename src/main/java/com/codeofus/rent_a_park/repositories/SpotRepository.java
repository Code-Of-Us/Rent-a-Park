package com.codeofus.rent_a_park.repositories;

import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.models.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {

    List<Spot> getSpotsByRenter(Person person);
    Spot getSpotById(Integer id);
    Optional<Spot> findOneById(Integer id);

}
