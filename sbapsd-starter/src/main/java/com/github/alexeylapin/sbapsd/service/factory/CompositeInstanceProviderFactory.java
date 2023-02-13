package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.InstanceProviderDef;
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
    public InstanceProvider create(InstanceProviderDef instanceProviderDef) {
        String type = instanceProviderDef.getType();
        InstanceProviderFactory instanceProviderFactory = factories.get(type);
        if (instanceProviderFactory == null) {
            throw new IllegalArgumentException("unknown instance provider type: " + type);
        }
        return instanceProviderFactory.create(instanceProviderDef);
    }

}
