package com.github.alexeylapin.sbapsd.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Contains registered {@link ServiceProvider}s
 */
public class ServiceProviderRegistry {

    private final Map<String, ServiceProvider> map;

    public ServiceProviderRegistry(Map<String, ServiceProvider> map) {
        this.map = map;
    }

    public Flux<ServiceProvider> findAll() {
        return Flux.fromIterable(map.values());
    }

    public Mono<ServiceProvider> findByName(String name) {
        return Mono.justOrEmpty(map.get(name));
    }

}
