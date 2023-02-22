package com.github.alexeylapin.sbapsd.service.v1;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class V1WebInstanceProvider implements InstanceProvider {

    private final WebClient client;

    public V1WebInstanceProvider(WebClient client) {
        this.client = client;
    }

    @Override
    public Flux<Instance> getInstances() {
        return client.get()
                .uri("/api/applications")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(V1Application.class))
                .map(this::convert);
    }

    private Instance convert(V1Application application) {
        return Instance.builder()
                .name(application.getName())
                .url(application.getServiceUrl())
                .status(application.getStatusInfo().getStatus())
                .metadata(application.getMetadata())
                .build();
    }

}
