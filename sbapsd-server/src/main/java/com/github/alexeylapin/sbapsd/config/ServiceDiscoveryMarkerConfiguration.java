package com.github.alexeylapin.sbapsd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Defines Marker bean to enable sbapsd service discovery autoconfiguration
 * @see EnableAdminServerServiceDiscovery
 * @see ServiceDiscoveryAutoConfiguration
 */
@Configuration(proxyBeanMethods = false)
public class ServiceDiscoveryMarkerConfiguration {

    @Bean
    public Marker adminServerServiceDiscoveryMarker() {
        return new Marker();
    }

    public static class Marker {

    }

}
