package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.v2.V2InstanceRegistryInstanceProvider;
import de.codecentric.boot.admin.server.services.InstanceRegistry;

public class V2InstanceRegistryInstanceProviderFactory implements InstanceProviderFactory {

    private final InstanceRegistry instanceRegistry;

    public V2InstanceRegistryInstanceProviderFactory(InstanceRegistry instanceRegistry) {
        this.instanceRegistry = instanceRegistry;
    }

    @Override
    public String getType() {
        return "registry-v2";
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        return new V2InstanceRegistryInstanceProvider(instanceRegistry);
    }

}
