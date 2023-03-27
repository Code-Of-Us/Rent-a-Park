package com.codeofus.rent_a_park;

import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

@SpringBootTest
public class ParkingAppApplicationTest extends IntegrationTest {
    @Value("${spring.application.name}")
    private String applicationName;
    @Lazy
    @Autowired
    private EurekaClient eurekaClient;

    @Test
    public void checkIfServiceRegistered() {
        await()
                .atMost(Duration.ofMinutes(2))
                .with()
                .pollInterval(Duration.ofSeconds(3))
                .until(() -> eurekaClient.getApplication(applicationName) != null);
    }
}

