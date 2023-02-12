package com.github.alexeylapin.sbapsd.web;

import com.github.alexeylapin.sbapsd.model.Item;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.InstanceProviderRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@ServiceDiscoveryController
@ResponseBody
public class InstanceController {

    private final InstanceProviderRegistry instanceProviderRegistry;

    public InstanceController(InstanceProviderRegistry instanceProviderRegistry) {
        this.instanceProviderRegistry = instanceProviderRegistry;
    }

    @GetMapping(path = "/service-discovery/prometheus/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Item> getInstances(@PathVariable String name) {
        return instanceProviderRegistry.findInstanceProvider(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND.value(),
                        "instance provider '" + name + "' is not found", null))
                .getItems();
    }

}
