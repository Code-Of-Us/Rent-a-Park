package com.codeofus.rent_a_park;

import lombok.experimental.FieldDefaults;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static lombok.AccessLevel.PRIVATE;

@Testcontainers
@SpringBootTest
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class IntegrationTest {

    static String REDIS_IMAGE = "redis:5.0.3-alpine";
    static int REDIS_PORT = 6379;
    static String POSTGRES_IMAGE = "postgres:14.7-alpine";
    static String EUREKA_IMAGE = "springcloud/eureka";
    static int EUREKA_PORT = 8761;
    static String KAFKA_IMAGE = "confluentinc/cp-kafka:7.3.0";


    static PostgreSQLContainer postgres = new PostgreSQLContainer(POSTGRES_IMAGE);
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse(REDIS_IMAGE))
            .withExposedPorts(REDIS_PORT);
    static GenericContainer<?> eurekaServer = new GenericContainer<>(DockerImageName.parse(EUREKA_IMAGE))
            .withExposedPorts(EUREKA_PORT);
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse(KAFKA_IMAGE));

    static {
        postgres.start();
        redis.start();
        eurekaServer.start();
        kafka.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
        System.setProperty("spring.redis.host", redis.getHost());
        System.setProperty("spring.redis.port", redis.getMappedPort(REDIS_PORT).toString());
        System.setProperty("eureka.client.service-url.defaultZone", "http://" + eurekaServer.getIpAddress() + ":" + eurekaServer.getFirstMappedPort() + "/eureka");
        System.setProperty("spring.kafka.properties.bootstrap.servers", kafka.getHost() + ":" + kafka.getFirstMappedPort());
    }
}
