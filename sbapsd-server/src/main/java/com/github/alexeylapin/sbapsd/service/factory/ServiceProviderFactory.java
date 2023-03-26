package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.ServiceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Instantiates {@link ServiceProvider} based on {@link ServiceProviderDef}
 */
public interface ServiceProviderFactory {

    ServiceProvider create(ServiceProviderDef serviceProviderDef);

    default Map<String, ServiceProvider> createAll(Map<String, ServiceProviderDef> serviceProviderDefs) {
        Validate.notNull(serviceProviderDefs, "serviceProviderDefs must not be null");
        Map<String, ServiceProvider> serviceProviders = new ConcurrentHashMap<>();
        for (Map.Entry<String, ServiceProviderDef> entry : serviceProviderDefs.entrySet()) {
            ServiceProviderDef serviceProviderDef = entry.getValue();
            if (serviceProviderDef.getName() == null) {
                serviceProviderDef.setName(entry.getKey());
            }
            ServiceProvider instanceProvider = create(serviceProviderDef);
            serviceProviders.put(entry.getKey(), instanceProvider);
        }
        return serviceProviders;
    }

}
