package com.github.alexeylapin.sbapsd.testing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@EnableAutoConfiguration
public class V3SBAClient {

    @Bean
    public HealthIndicator healthIndicator(@Value("${sba.client.status}") String status) {
        return () -> Health.status(new Status(status)).build();
    }

}
