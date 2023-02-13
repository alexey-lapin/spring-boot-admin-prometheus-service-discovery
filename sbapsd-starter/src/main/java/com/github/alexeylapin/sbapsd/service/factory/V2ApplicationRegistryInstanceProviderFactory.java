package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.InstanceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.v2.V2ApplicationRegistryInstanceProvider;
import de.codecentric.boot.admin.server.services.ApplicationRegistry;

public class V2ApplicationRegistryInstanceProviderFactory implements InstanceProviderFactory {

    private final ApplicationRegistry applicationRegistry;

    public V2ApplicationRegistryInstanceProviderFactory(ApplicationRegistry applicationRegistry) {
        this.applicationRegistry = applicationRegistry;
    }

    @Override
    public String getType() {
        return "registry-v2";
    }

    @Override
    public InstanceProvider create(InstanceProviderDef instanceProviderDef) {
        return new V2ApplicationRegistryInstanceProvider(item -> true, instanceProviderDef.getLabels(), applicationRegistry);
    }

}
