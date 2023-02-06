package com.github.alexeylapin.sbapsd.service;

import java.util.Map;
import java.util.Optional;

public class ServerAccessorRegistry {

    private final Map<String, ServerAccessor> map;

    public ServerAccessorRegistry(Map<String, ServerAccessor> map) {
        this.map = map;
    }

    public Optional<ServerAccessor> findServerAccessor(String name) {
        return Optional.ofNullable(map.get(name));
    }

}
