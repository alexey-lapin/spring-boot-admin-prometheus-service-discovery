package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Context for {@link LabelContributor}
 */
@Getter
public class LabelContribution {

    private final String serviceProviderName;
    private final Map<String, String> staticLabels;
    private final String appName;
    private final List<Instance> instances;

    @Builder
    public LabelContribution(String serviceProviderName,
                             Map<String, String> staticLabels,
                             String appName,
                             List<Instance> instances) {
        this.serviceProviderName = Validate.notNull(serviceProviderName, "serviceProviderName must not be null");
        this.staticLabels = Validate.notNull(staticLabels, "staticLabels must not be null");
        this.appName = Validate.notNull(appName, "appName must not be null");
        this.instances = Validate.notNull(instances, "instances must not be null");
    }

}
