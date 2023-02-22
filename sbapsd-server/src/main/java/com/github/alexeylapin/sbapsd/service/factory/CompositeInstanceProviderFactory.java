package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompositeInstanceProviderFactory implements InstanceProviderFactory {

    private final Map<String, InstanceProviderFactory> factories;

    public CompositeInstanceProviderFactory(List<InstanceProviderFactory> factories) {
        this.factories = factories.stream()
                .collect(Collectors.toMap(InstanceProviderFactory::getType, Function.identity()));
    }

    @Override
    public String getType() {
        return "composite";
    }

    @Override
    public InstanceProvider create(ServiceProviderDef serviceProviderDef) {
        String type = serviceProviderDef.getType();
        InstanceProviderFactory instanceProviderFactory = factories.get(type);
        if (instanceProviderFactory == null) {
            throw new IllegalArgumentException("unknown instance provider type: " + type);
        }
        return instanceProviderFactory.create(serviceProviderDef);
    }

}
