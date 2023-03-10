package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;
import com.github.alexeylapin.sbapsd.service.registry.InstanceRegistryInstanceProvider;
import de.codecentric.boot.admin.server.services.InstanceRegistry;

public class InstanceRegistryInstanceProviderFactory implements InstanceProviderFactory {

    public static final String TYPE_REGISTRY = "registry";

    private final InstanceRegistry instanceRegistry;

    public InstanceRegistryInstanceProviderFactory(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = Validate.notNull(instanceRegistry, "instanceRegistry must not be null");
    }

    @Override
    public String getType() {
        return TYPE_REGISTRY;
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        Validate.notNull(serviceProviderDef, "serviceProviderDef must not be null");
        return new InstanceRegistryInstanceProvider(instanceRegistry);
    }

}
