package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;

import java.net.URI;
import java.util.List;
import java.util.Map;

public interface LabelContributors {

    class CompositeLabelContributor implements LabelContributor {

        private final List<LabelContributor> delegates;

        public CompositeLabelContributor(List<LabelContributor> delegates) {
            this.delegates = delegates;
        }

        @Override
        public void contribute(Map<String, String> labels, LabelContribution labelContribution) {
            delegates.forEach(delegate -> delegate.contribute(labels, labelContribution));
        }

    }

    @RequiredArgsConstructor
    @Getter
    abstract class AbstractLabelContributor implements LabelContributor {

        private final int order;

    }

    class StaticLabelContributor extends AbstractLabelContributor implements Ordered {

        public StaticLabelContributor(int order) {
            super(order);
        }

        @Override
        public void contribute(Map<String, String> labels, LabelContribution labelContribution) {
            Validate.notNull(labelContribution, "labelContribution must not be null");
            labels.putAll(labelContribution.getStaticLabels());
        }

    }

    class AppNameLabelContributor extends AbstractLabelContributor implements Ordered {

        public static final String LABEL_APP_NAME = "__meta_discovery_app_name";

        public AppNameLabelContributor(int order) {
            super(order);
        }

        @Override
        public void contribute(Map<String, String> labels, LabelContribution labelContribution) {
            Validate.notNull(labelContribution, "labelContribution must not be null");
            if (labelContribution.getAppName() != null) {
                labels.put(LABEL_APP_NAME, labelContribution.getAppName());
            }
        }

    }

    class ActuatorPathLabelContributor extends AbstractLabelContributor implements Ordered {

        public static final String LABEL_ACTUATOR_PATH = "__meta_discovery_actuator_path";

        public ActuatorPathLabelContributor(int order) {
            super(order);
        }

        @Override
        public void contribute(Map<String, String> labels, LabelContribution labelContribution) {
            Validate.notNull(labelContribution, "labelContribution must not be null");
            labelContribution.getInstances().stream()
                    .map(Instance::getManagementUrl)
                    .findAny()
                    .ifPresent(url -> {
                        URI uri = URI.create(url);
                        String path = uri.getPath();
                        labels.put(LABEL_ACTUATOR_PATH, path);
                    });
        }

    }

    class ServerNameLabelContributor extends AbstractLabelContributor implements Ordered {

        public static final String LABEL_SERVER_NAME = "__meta_discovery_server_name";

        public ServerNameLabelContributor(int order) {
            super(order);
        }

        @Override
        public void contribute(Map<String, String> labels, LabelContribution labelContribution) {
            Validate.notNull(labelContribution, "labelContribution must not be null");
            labels.put(LABEL_SERVER_NAME, labelContribution.getServiceProviderName());
        }

    }

}
