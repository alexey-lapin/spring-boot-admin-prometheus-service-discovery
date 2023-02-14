package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Service;
import reactor.core.publisher.Flux;

public interface ServiceProvider {

    Flux<Service> getServices();

}
