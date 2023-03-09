package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonServiceWithCacheTests {
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ParkingMapper mapper;

    public static GenericContainer redis = new GenericContainer(DockerImageName.parse("redis:5.0.3-alpine"))
            .withExposedPorts(6379);

    @BeforeAll
    public static void startRedis() {
        redis.start();
    }

    @AfterAll
    public static void stopRedis() {
        redis.stop();
    }

    @Test
    public void getAllPersonsWithCache() {
        Person person = new Person(1, "firstname", "lastname", "ZG2020-IS", null, null);
        when(personRepository.findAll()).thenReturn(List.of(person));

        List<PersonDto> persons1 = personService.getAll();
        verify(personRepository, times(1)).findAll();

        List<PersonDto> persons2 = personService.getAll();
        verifyNoMoreInteractions(personRepository);
        assertThat(persons1).isEqualTo(persons2);
    }

}
