package com.github.alexeylapin.sbapsd.service.factory;

import com.github.alexeylapin.sbapsd.config.def.ServiceProviderDef;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;

public interface InstanceProviderFactory {

    String getType();

    InstanceProvider create(ServiceProviderDef serviceProviderDef);

}
