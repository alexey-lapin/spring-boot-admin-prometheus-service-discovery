package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.model.Service;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Provides {@link Service}s using configured {@link InstanceProvider}
 */
public class DefaultServiceProvider implements ServiceProvider {

    private final String name;
    private final InstanceProvider instanceProvider;
    private final Predicate<Instance> instancePredicate;
    private final Map<String, String> staticLabels;
    private final LabelContributor labelContributor;

    public DefaultServiceProvider(String name,
                                  InstanceProvider instanceProvider,
                                  Predicate<Instance> instancePredicate,
                                  Map<String, String> staticLabels,
                                  LabelContributor labelContributor) {
        this.name = Validate.notNull(name, "name must not be null");
        this.instanceProvider = Validate.notNull(instanceProvider, "instanceProvider must not be null");
        this.instancePredicate = Validate.notNull(instancePredicate, "instancePredicate must not be null");
        this.staticLabels = Validate.notNull(staticLabels, "staticLabels must not be null");
        this.labelContributor = Validate.notNull(labelContributor, "labelContributor must not be null");
    }

    @Override
    public Flux<Service> getServices() {
        return instanceProvider.getInstances()
                .filter(instancePredicate)
                .groupBy(Instance::getName)
                .flatMap(grouped -> toService(grouped.key(), grouped), Integer.MAX_VALUE);
    }

    protected Flux<Service> toService(String appName, Flux<Instance> instances) {
        return instances.collectList().flatMapMany((instanceList) -> {
            Map<String, List<Instance>> instancesByPath = instanceList.stream()
                    .filter(instance -> Objects.nonNull(instance.getManagementUrl()))
                    .collect(Collectors.groupingBy(instance -> URI.create(instance.getManagementUrl()).getPath()));
            List<Service> services = new ArrayList<>();
            for (List<Instance> instancesWithTheSamePath : instancesByPath.values()) {
                List<String> targets = instancesWithTheSamePath.stream()
                        .map(Instance::getManagementUrl)
                        .map(URI::create)
                        .map(uri -> uri.getHost() + ":" + uri.getPort())
                        .collect(Collectors.toList());

                Map<String, String> labels = new HashMap<>();
                LabelContribution labelContribution = LabelContribution.builder()
                        .serviceProviderName(name)
                        .staticLabels(staticLabels)
                        .appName(appName)
                        .instances(instancesWithTheSamePath)
                        .build();

                labelContributor.contribute(labels, labelContribution);

                Service service = new Service(targets, labels);
                services.add(service);
            }
            return Flux.fromIterable(services);
        });
    }

}
