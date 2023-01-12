package com.github.alexeylapin.sbapsd;

import com.github.alexeylapin.sbapsd.model.Item;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;
import reactor.core.publisher.Flux;

public class LocalInstanceProvider implements InstanceProvider {

    private final ApplicationRegistry applicationRegistry;

    public LocalInstanceProvider(ApplicationRegistry applicationRegistry) {
        this.applicationRegistry = applicationRegistry;
    }

    @Override
    public Flux<Item> getItems() {
        return applicationRegistry.getApplications().map(item -> new Item());
    }

}
