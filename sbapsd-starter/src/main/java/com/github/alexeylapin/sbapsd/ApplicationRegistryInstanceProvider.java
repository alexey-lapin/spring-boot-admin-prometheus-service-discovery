package com.github.alexeylapin.sbapsd;

import com.github.alexeylapin.sbapsd.model.Item;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationRegistryInstanceProvider implements InstanceProvider {

    private final ApplicationRegistry applicationRegistry;

    public ApplicationRegistryInstanceProvider(ApplicationRegistry applicationRegistry) {
        this.applicationRegistry = applicationRegistry;
    }

    @Override
    public Flux<Item> getItems() {
        return applicationRegistry.getApplications()
                .map(item -> new Item(null, null));
    }

    private Item convert(Application application) {
        List<String> targets = application.getInstances().stream()
                .map(item -> item.getRegistration().getServiceUrl())
                .collect(Collectors.toList());
        Map<String, String> labels = new HashMap<>();
        labels.put("__app_name", application.getName());
        return new Item(targets, labels);
    }

}
