package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.model.Service;
import com.github.alexeylapin.sbapsd.service.DefaultServiceProvider;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.ServiceProvider;

import java.util.Map;
import java.util.function.Predicate;

public class DefaultServiceProviderFactory implements ServiceProviderFactory {

    private final InstanceProviderFactory instanceProviderFactory;
    private final FilterFactory filterFactory;

    public DefaultServiceProviderFactory(InstanceProviderFactory instanceProviderFactory, FilterFactory filterFactory) {
        this.instanceProviderFactory = instanceProviderFactory;
        this.filterFactory = filterFactory;
    }

    @Override
    public ServiceProvider create(ServiceProviderDef serviceProviderDef) {
        InstanceProvider instanceProvider = instanceProviderFactory.create(serviceProviderDef);
        Predicate<Instance> predicate = filterFactory.createAll(serviceProviderDef.getFilters());
        return new DefaultServiceProvider(instanceProvider, predicate, serviceProviderDef.getLabels());
    }

}
