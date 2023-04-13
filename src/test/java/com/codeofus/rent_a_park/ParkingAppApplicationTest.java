package com.codeofus.rent_a_park;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

import static org.awaitility.Awaitility.await;

public class ParkingAppApplicationTest extends IntegrationTest {
    @Value("${spring.application.name}")
    String applicationName;

    @Value("${eureka.client.service-url.defaultZone}")
    String eurekaUrl;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void checkIfServiceRegistered() {
        await()
                .atMost(Duration.ofMinutes(2))
                .with()
                .pollInterval(Duration.ofSeconds(3))
                .until(() -> {
                    String url = eurekaUrl + "/apps/" + applicationName;
                    String response = restTemplate.getForObject(url, String.class);
                    assert response != null;
                    return response.contains(applicationName);
                });
    }
}

