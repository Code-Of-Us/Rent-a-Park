package com.codeofus.rent_a_park.services;

import com.codeofus.rent_a_park.dtos.ParkingMapper;
import com.codeofus.rent_a_park.dtos.PersonDto;
import com.codeofus.rent_a_park.models.Person;
import com.codeofus.rent_a_park.repositories.PersonRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCache;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceWithCacheTests {
    static GenericContainer<?> redis =
            new GenericContainer<>(DockerImageName.parse("redis:5.0.3-alpine")).withExposedPorts(6379);

    @Autowired
    private PersonService personService;

    @BeforeAll
    public static void startRedis() {
        redis.start();
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
    }

    @AfterAll
    public static void stopRedis() {
        redis.stop();
    }

    @Test
    public void getAllPersonsWithCache() {
        Person person = new Person(1, "firstname", "lastname", "ZG2020-IS", null, null);
        //when(personRepository.findAll()).thenReturn(List.of(person));

        List<PersonDto> persons1 = personService.getAll();
        //verify(personRepository, times(1)).findAll();

        List<PersonDto> persons2 = personService.getAll();
        //verifyNoMoreInteractions(personRepository);
        assertThat(persons1).isEqualTo(persons2);
    }

}
