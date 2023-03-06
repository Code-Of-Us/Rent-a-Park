package com.codeofus.rent_a_park.repositories;

import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {

    List<Spot> getSpotsByRenter(User user);

}
