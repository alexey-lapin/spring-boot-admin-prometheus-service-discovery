package com.github.alexeylapin.sbapsd.service.v2;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class V2WebInstanceProvider implements InstanceProvider {

    private final WebClient client;

    public V2WebInstanceProvider(WebClient client) {
        this.client = client;
    }

    @Override
    public Flux<Instance> getInstances() {
        return client.get()
                .uri("/instances")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(V2Instance.class))
                .map(this::convert);
    }

    private Instance convert(V2Instance instance) {
        return Instance.builder()
                .name(instance.getRegistration().getName())
                .url(instance.getRegistration().getServiceUrl())
                .status(instance.getStatusInfo().getStatus())
                .tags(instance.getTags())
                .metadata(instance.getRegistration().getMetadata())
                .build();
    }

}
