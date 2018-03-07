package io.pitchcast.pitcherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PitcherServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(PitcherServiceApp.class, args);
    }

}
