package com.github.alexeylapin.sbapsd.service.v2;

import com.github.alexeylapin.sbapsd.model.Item;
import com.github.alexeylapin.sbapsd.service.AbstractInstanceProvider;
import de.codecentric.boot.admin.server.domain.entities.Application;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class V2ApplicationRegistryInstanceProvider extends AbstractInstanceProvider {

    private final ApplicationRegistry applicationRegistry;

    public V2ApplicationRegistryInstanceProvider(Predicate<Item> filter,
                                                 Map<String, String> labels,
                                                 ApplicationRegistry applicationRegistry) {
        super(filter, labels);
        this.applicationRegistry = applicationRegistry;
    }

    @Override
    public Flux<Item> getItems() {
        return applicationRegistry.getApplications().map(this::convert).filter(getFilter());
    }

    private Item convert(Application application) {
        List<String> targets = application.getInstances().stream()
                .map(item -> item.getRegistration().getServiceUrl())
                .collect(Collectors.toList());
        getLabels().put("__app_name", application.getName());
        return new Item(targets, getLabels());
    }

}
