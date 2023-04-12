package com.codeofus.rent_a_park.mappers;

import com.codeofus.rent_a_park.IntegrationTest;
import com.codeofus.rent_a_park.dtos.SpotDTO;
import com.codeofus.rent_a_park.models.Spot;
import com.codeofus.rent_a_park.stubs.PersonDTOStub;
import com.codeofus.rent_a_park.stubs.PersonStub;
import com.codeofus.rent_a_park.stubs.SpotDTOStub;
import com.codeofus.rent_a_park.stubs.SpotStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpotMapperTests extends IntegrationTest {

    @Autowired
    SpotMapper spotMapper;

    Spot spot;
    SpotDTO spotDTO;

    @BeforeEach
    void setUp() {
        spotDTO = SpotDTOStub.givenSpotDtoStubBuilder().renter(PersonDTOStub.givenPersonDtoStub()).address("Zagrebacka 1").parkingZone("4.2").build();
        spot = SpotStub.givenSpotStubBuilder().renter(PersonStub.givenPersonStub()).address("Zagrebacka 1").parkingZone("4.2").build();
    }

    @Test
    public void testSpotDtoToSpot() {
        Spot mapperSpot = spotMapper.spotDTOtoSpot(spotDTO);
        assertEquals(spot.getRenter(), mapperSpot.getRenter());
        assertEquals(spot.getAddress(), mapperSpot.getAddress());
        assertEquals(spot.getParkingZone(), mapperSpot.getParkingZone());
    }

    @Test
    public void testSpotToSpotDto() {
        SpotDTO mapperSpot = spotMapper.spotToSpotDTO(spot);
        assertEquals(spotDTO.getRenter(), mapperSpot.getRenter());
        assertEquals(spotDTO.getAddress(), mapperSpot.getAddress());
        assertEquals(spotDTO.getParkingZone(), mapperSpot.getParkingZone());
    }
}
