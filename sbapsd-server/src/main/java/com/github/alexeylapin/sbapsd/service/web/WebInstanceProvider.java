package com.github.alexeylapin.sbapsd.service.web;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebInstanceProvider implements InstanceProvider {

    private final WebClient client;

    public WebInstanceProvider(WebClient client) {
        this.client = client;
    }

    @Override
    public Flux<Instance> getInstances() {
        return client.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(WebInstancePayload.class))
                .map(this::convert);
    }

    private Instance convert(WebInstancePayload application) {
        return Instance.builder()
                .name(application.getName())
                .url(application.getServiceUrl())
                .status(application.getStatusInfo().getStatus())
                .metadata(application.getMetadata())
                .tags(application.getTags())
                .build();
    }

}
