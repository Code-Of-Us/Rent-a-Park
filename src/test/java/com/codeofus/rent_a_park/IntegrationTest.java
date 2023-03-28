package com.codeofus.rent_a_park;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
@Testcontainers
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class IntegrationTest {

    static String REDIS_IMAGE = "redis:5.0.3-alpine";
    static int REDIS_PORT = 6379;
    static String POSTGRES_IMAGE = "postgres:14.7-alpine";
    static String EUREKA_IMAGE = "ghcr.io/code-of-us/eureka-server:latest";
    static int EUREKA_PORT = 8761;

    static PostgreSQLContainer postgres = new PostgreSQLContainer(POSTGRES_IMAGE);
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
            .withExposedPorts(REDIS_PORT);
    static GenericContainer<?> eurekaServer = new GenericContainer<>(EUREKA_IMAGE)
            .withExposedPorts(EUREKA_PORT);

    @BeforeAll
    public static void startContainers() {
        postgres.start();
        redis.start();
        eurekaServer.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(REDIS_PORT).toString());
        System.setProperty("eureka.client.service-url.defaultZone", "http://" + eurekaServer.getIpAddress() + ":" + eurekaServer.getMappedPort(EUREKA_PORT) + "/eureka");
    }

    @AfterAll
    public static void stopContainers() {
        postgres.stop();
        redis.stop();
        eurekaServer.stop();
    }
}
