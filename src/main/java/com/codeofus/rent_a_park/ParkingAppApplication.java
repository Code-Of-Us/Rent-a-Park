package com.codeofus.rent_a_park;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParkingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingAppApplication.class, args);
    }

}