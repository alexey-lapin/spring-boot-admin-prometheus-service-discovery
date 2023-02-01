package com.github.alexeylapin.sbapsd.web;

import com.github.alexeylapin.sbapsd.InstanceProvider;
import com.github.alexeylapin.sbapsd.model.Item;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@ServiceDiscoveryController
@ResponseBody
public class InstanceController {

    private final InstanceProvider instanceProvider;

    public InstanceController(InstanceProvider instanceProvider) {
        this.instanceProvider = instanceProvider;
    }

    @GetMapping(path = "/sd/prometheus/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Item> getInstances(@PathVariable String name) {
        return instanceProvider.getItems();
    }

}
