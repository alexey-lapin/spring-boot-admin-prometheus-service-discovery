package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompositeInstanceProviderFactory implements InstanceProviderFactory {

    public static final String TYPE_COMPOSITE = "composite";

    private final Map<String, InstanceProviderFactory> factories;

    public CompositeInstanceProviderFactory(List<InstanceProviderFactory> factories) {
        Validate.notNull(factories, "factories must not be null");
        this.factories = factories.stream()
                .collect(Collectors.toMap(InstanceProviderFactory::getType, Function.identity()));
    }

    @Override
    public String getType() {
        return TYPE_COMPOSITE;
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        Validate.notNull(serviceProviderDef, "serviceProviderDef must not be null");
        String type = serviceProviderDef.getType();
        Validate.notNull(type, "serviceProviderDef.type must not be null");

        InstanceProviderFactory instanceProviderFactory = factories.get(type);
        Validate.isTrue(instanceProviderFactory != null, "unknown instance provider type: %s", type);
        return instanceProviderFactory.create(serviceProviderDef);
    }

}
