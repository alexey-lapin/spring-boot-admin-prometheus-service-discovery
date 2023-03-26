package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import reactor.core.publisher.Flux;

/**
 * Provides {@link Instance}s
 */
public interface InstanceProvider {

    Flux<Instance> getInstances();

}
