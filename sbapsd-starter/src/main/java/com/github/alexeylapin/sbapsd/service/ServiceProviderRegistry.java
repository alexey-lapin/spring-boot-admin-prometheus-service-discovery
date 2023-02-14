package com.github.alexeylapin.sbapsd.service;

import java.util.Map;
import java.util.Optional;

public class ServiceProviderRegistry {

    private final Map<String, ServiceProvider> map;

    public ServiceProviderRegistry(Map<String, ServiceProvider> map) {
        this.map = map;
    }

    public Optional<ServiceProvider> findServiceProvider(String name) {
        return Optional.ofNullable(map.get(name));
    }

}
