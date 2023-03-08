package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import org.springframework.core.Ordered;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface LabelContributors {

    class CompositeLabelContributor implements LabelContributor {

        private final List<LabelContributor> delegates;

        public CompositeLabelContributor(List<LabelContributor> delegates) {
            this.delegates = delegates;
        }

        @Override
        public void contribute(Map<String, String> labels, String name, List<Instance> instances) {
            delegates.forEach(delegate -> delegate.contribute(labels, name, instances));
        }

    }

    class AppNameLabelContributor implements LabelContributor, Ordered {

        public static final String LABEL_APP_NAME = "__meta_discovery_app_name";

        @Override
        public int getOrder() {
            return 0;
        }

        @Override
        public void contribute(Map<String, String> labels, String name, List<Instance> instances) {
            if (name != null) {
                labels.put(LABEL_APP_NAME, name);
            }
        }

    }

    class ActutorPathLabelContributor implements LabelContributor, Ordered {

        public static final String LABEL_ACTUATOR_PATH = "__meta_discovery_actuator_path";

        @Override
        public int getOrder() {
            return 10;
        }

        @Override
        public void contribute(Map<String, String> labels, String name, List<Instance> instances) {
            instances.stream()
                    .map(Instance::getManagementUrl)
                    .filter(Objects::nonNull)
                    .findAny()
                    .ifPresent(url -> {
                        URI uri = URI.create(url);
                        String path = uri.getPath();
                        labels.put("__meta_discovery_actuator_path", path);
                    });
        }

    }

}
