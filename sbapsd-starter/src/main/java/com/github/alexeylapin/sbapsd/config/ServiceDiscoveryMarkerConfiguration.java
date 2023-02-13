package com.github.alexeylapin.sbapsd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ServiceDiscoveryMarkerConfiguration {

    @Bean
    public Marker adminServerServiceDiscoveryMarker() {
        return new Marker();
    }

    public static class Marker {

    }

}
