package com.github.alexeylapin.sbapsd.service;

import java.util.Map;
import java.util.Optional;

public class InstanceProviderRegistry {

    private final Map<String, InstanceProvider> map;

    public InstanceProviderRegistry(Map<String, InstanceProvider> map) {
        this.map = map;
    }

    public Optional<InstanceProvider> findInstanceProvider(String name) {
        return Optional.ofNullable(map.get(name));
    }

}
