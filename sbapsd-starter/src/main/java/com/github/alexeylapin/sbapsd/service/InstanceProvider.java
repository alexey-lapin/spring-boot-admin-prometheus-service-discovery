package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import reactor.core.publisher.Flux;

public interface InstanceProvider {

    Flux<Instance> getInstances();

}
