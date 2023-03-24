package com.codeofus.rent_a_park;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.awaitility.Awaitility.await;

@SpringBootTest
public class ParkingAppApplicationTest extends IntegrationTest {
    static final String EUREKA_URL = "http://localhost:" + eurekaServer.getMappedPort(EUREKA_PORT) + "/eureka/apps";

    @Value("${spring.application.name}")
    private String applicationName;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void checkIfServiceRegistered() {
        await()
            .atMost(Duration.ofMinutes(2))
            .with()
            .pollInterval(Duration.ofSeconds(3))
            .until(() -> checkIfApplicationRegistered(applicationName));
    }

    private boolean checkIfApplicationRegistered(String applicationName) {
        List<String> applications = getEurekaRegisteredApplications();
        return applications.contains(applicationName);
    }

    private List<String> getEurekaRegisteredApplications() {
        ResponseEntity<String> response = restTemplate.getForEntity(EUREKA_URL, String.class);
        String responseBody = response.getBody();
        List<String> applications = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode applicationsNode = rootNode.get("applications").get("application");

            for (JsonNode applicationNode : applicationsNode) {
                String appName = applicationNode.get("name").asText();
                applications.add(appName);
            }
        } catch (JsonProcessingException e) {}
        return applications;
    }
}

