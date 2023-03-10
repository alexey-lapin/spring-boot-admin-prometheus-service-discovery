package com.github.alexeylapin.sbapsd.web;

import com.github.alexeylapin.sbapsd.model.Service;
import com.github.alexeylapin.sbapsd.service.ServiceProvider;
import com.github.alexeylapin.sbapsd.service.ServiceProviderRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ServiceDiscoveryControllerMarker
@ResponseBody
public class ServiceDiscoveryController {

    private final ServiceProviderRegistry serviceProviderRegistry;

    public ServiceDiscoveryController(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }

    @GetMapping(path = "/service-discovery/prometheus", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Service> getServices() {
        return serviceProviderRegistry.findAll()
                .concatMap(ServiceProvider::getServices);
    }

    @GetMapping(path = "/service-discovery/prometheus/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Service> getServices(@PathVariable("name") String name) {
        return serviceProviderRegistry.findByName(name)
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND.value(),
                        "service provider '" + name + "' is not found!", null)))
                .flatMapMany(ServiceProvider::getServices);
    }

}
