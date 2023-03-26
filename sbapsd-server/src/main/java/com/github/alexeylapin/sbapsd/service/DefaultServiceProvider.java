/*
 * MIT License
 *
 * Copyright (c) 2023 Alexey Lapin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
