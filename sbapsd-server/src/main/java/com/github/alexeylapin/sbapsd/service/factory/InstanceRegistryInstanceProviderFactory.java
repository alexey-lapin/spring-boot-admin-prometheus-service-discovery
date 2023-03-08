package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.registry.InstanceRegistryInstanceProvider;
import de.codecentric.boot.admin.server.services.InstanceRegistry;

public class InstanceRegistryInstanceProviderFactory implements InstanceProviderFactory {

    private final InstanceRegistry instanceRegistry;

    public InstanceRegistryInstanceProviderFactory(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public String getType() {
        return "registry";
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        return new InstanceRegistryInstanceProvider(instanceRegistry);
    }

}
