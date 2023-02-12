package com.github.alexeylapin.sbapsd.service.v2;

import com.github.alexeylapin.sbapsd.model.Item;
import com.github.alexeylapin.sbapsd.service.AbstractInstanceProvider;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class V2WebInstanceProvider extends AbstractInstanceProvider {

    private final WebClient client;

    public V2WebInstanceProvider(Predicate<Item> filter, Map<String, String> labels, WebClient client) {
        super(filter, labels);
        this.client = client;
    }

    @Override
    public Flux<Item> getItems() {
        return client.get()
                .uri("/applications")
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(V2Application.class))
                .map(this::convert)
                .filter(getFilter());
    }

    private Item convert(V2Application application) {
        List<String> targets = application.getInstances().stream()
                .map(item -> item.getRegistration().getServiceUrl())
                .collect(Collectors.toList());
        getLabels().put("__app_name", application.getName());
        return new Item(targets, getLabels());
    }

}
