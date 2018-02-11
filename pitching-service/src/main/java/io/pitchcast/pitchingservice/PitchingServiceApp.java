package io.pitchcast.pitchingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PitchingServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PitchingServiceApp.class, args);
    }

}
