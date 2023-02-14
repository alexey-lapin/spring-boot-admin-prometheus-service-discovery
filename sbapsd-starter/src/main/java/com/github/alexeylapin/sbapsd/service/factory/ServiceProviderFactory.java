package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.ServiceProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ServiceProviderFactory {

    ServiceProvider create(ServiceProviderDef serviceProviderDef);

    default Map<String, ServiceProvider> createAll(Map<String, ServiceProviderDef> definitions) {
        Map<String, ServiceProvider> map = new ConcurrentHashMap<>();
        for (Map.Entry<String, ServiceProviderDef> entry : definitions.entrySet()) {
            ServiceProvider instanceProvider = create(entry.getValue());
            map.put(entry.getKey(), instanceProvider);
        }
        return map;
    }

}
