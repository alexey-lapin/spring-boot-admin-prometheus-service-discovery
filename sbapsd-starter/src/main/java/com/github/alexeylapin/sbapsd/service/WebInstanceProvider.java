package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Application;
import com.github.alexeylapin.sbapsd.model.Item;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebInstanceProvider implements InstanceProvider {

    private final WebClient client;

    public WebInstanceProvider(WebClient client) {
        this.client = client;
    }

    @Override
    public Flux<Item> getItems() {
        return client.get()
                .uri("/applications")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(Application.class))
                .map(WebInstanceProvider::convert);
    }

    private static Item convert(Application application) {
        List<String> targets = application.getInstances().stream()
                .map(item -> item.getRegistration().getServiceUrl())
                .collect(Collectors.toList());
        Map<String, String> labels = new HashMap<>();
        labels.put("__app_name", application.getName());
        return new Item(targets, labels);
    }

}
