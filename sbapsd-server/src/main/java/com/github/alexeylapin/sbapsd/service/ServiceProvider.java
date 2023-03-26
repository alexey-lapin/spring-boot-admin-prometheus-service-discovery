package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Service;
import reactor.core.publisher.Flux;

/**
 * Provides {@link Service}s
 */
public interface ServiceProvider {

    Flux<Service> getServices();

}
