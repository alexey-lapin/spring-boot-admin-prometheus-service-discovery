package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.model.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DefaultServiceProvider implements ServiceProvider {

    private final InstanceProvider instanceProvider;
    private final Predicate<Instance> predicate;
    private final Map<String, String> labels;

    public DefaultServiceProvider(InstanceProvider instanceProvider,
                                  Predicate<Instance> predicate,
                                  Map<String, String> labels) {
        this.instanceProvider = instanceProvider;
        this.predicate = predicate;
        this.labels = labels;
    }

    @Override
    public Flux<Service> getServices() {
        return instanceProvider.getInstances()
                .filter(predicate)
                .groupBy(Instance::getName)
                .flatMap(grouped -> toService(grouped.key(), grouped), Integer.MAX_VALUE);
    }

    protected Mono<Service> toService(String name, Flux<Instance> instances) {
        return instances.collectList().map((instanceList) -> {
            List<String> targets = instanceList.stream()
                    .map(Instance::getUrl)
                    .collect(Collectors.toList());
            Map<String, String> labels = new HashMap<>(this.labels);
            labels.put("__app_name", name);
            return new Service(targets, labels);
        });
    }

}
