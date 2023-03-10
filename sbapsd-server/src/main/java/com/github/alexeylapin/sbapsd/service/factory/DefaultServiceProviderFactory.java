package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.DefaultServiceProvider;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.LabelContributor;
import com.github.alexeylapin.sbapsd.service.ServiceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;

import java.util.Collections;
import java.util.function.Predicate;

public class DefaultServiceProviderFactory implements ServiceProviderFactory {

    private final InstanceProviderFactory instanceProviderFactory;
    private final FilterFactory filterFactory;
    private final LabelContributor labelContributor;

    public DefaultServiceProviderFactory(InstanceProviderFactory instanceProviderFactory,
                                         FilterFactory filterFactory,
                                         LabelContributor labelContributor) {
        this.instanceProviderFactory = instanceProviderFactory;
        this.filterFactory = filterFactory;
        this.labelContributor = labelContributor;
    }

    @Override
    public ServiceProvider create(ServiceProviderDef serviceProviderDef) {
        Validate.notNull(serviceProviderDef, "serviceProviderDef must not be null");
        InstanceProvider instanceProvider = instanceProviderFactory.create(serviceProviderDef);
        Predicate<Instance> instancePredicate = filterFactory.createAll(serviceProviderDef.getFilters());
        return new DefaultServiceProvider(serviceProviderDef.getName(),
                instanceProvider,
                instancePredicate,
                Collections.unmodifiableMap(serviceProviderDef.getLabels()),
                labelContributor);
    }

}
