package com.github.alexeylapin.sbapsd.service.web;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebInstanceProvider implements InstanceProvider {

    private final WebClient client;

    public WebInstanceProvider(WebClient client) {
        this.client = Validate.notNull(client, "client must not be null");
    }

    @Override
    public Flux<Instance> getInstances() {
        return client.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(WebInstancePayload.class))
                .map(this::convert);
    }

    private Instance convert(WebInstancePayload payload) {
        return Instance.builder()
                .name(payload.getName())
                .serviceUrl(payload.getServiceUrl())
                .managementUrl(payload.getManagementUrl())
                .status(payload.getStatusInfo().getStatus())
                .metadata(payload.getMetadata())
                .tags(payload.getTags())
                .build();
    }

}
