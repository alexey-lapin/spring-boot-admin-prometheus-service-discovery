package com.github.alexeylapin.sbapsd.service.registry;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import de.codecentric.boot.admin.server.services.InstanceRegistry;
import reactor.core.publisher.Flux;

public class InstanceRegistryInstanceProvider implements InstanceProvider {

    private final InstanceRegistry instanceRegistry;

    public InstanceRegistryInstanceProvider(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public Flux<Instance> getInstances() {
        return instanceRegistry.getInstances().map(this::convert);
    }

    private Instance convert(de.codecentric.boot.admin.server.domain.entities.Instance instance) {
        return Instance.builder()
                .name(instance.getRegistration().getName())
                .url(instance.getRegistration().getServiceUrl())
                .status(instance.getStatusInfo().getStatus())
                .tags(instance.getTags().getValues())
                .metadata(instance.getRegistration().getMetadata())
                .build();
    }

}
