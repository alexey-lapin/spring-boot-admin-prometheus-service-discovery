/*
 * MIT License
 *
 * Copyright (c) 2023 Alexey Lapin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.alexeylapin.sbapsd.web;

import com.github.alexeylapin.sbapsd.model.Service;
import com.github.alexeylapin.sbapsd.service.ServiceProvider;
import com.github.alexeylapin.sbapsd.service.ServiceProviderRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ServiceDiscoveryControllerMarker
@ResponseBody
public class ServiceDiscoveryController {

    private final ServiceProviderRegistry serviceProviderRegistry;

    public ServiceDiscoveryController(ServiceProviderRegistry serviceProviderRegistry) {
        this.serviceProviderRegistry = serviceProviderRegistry;
    }

    @GetMapping(path = "/service-discovery/prometheus", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Service> getServices() {
        return serviceProviderRegistry.findAll()
                .concatMap(ServiceProvider::getServices);
    }

    @GetMapping(path = "/service-discovery/prometheus/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Service> getServices(@PathVariable("name") String name) {
        return serviceProviderRegistry.findByName(name)
                .switchIfEmpty(Mono.error(() -> new ResponseStatusException(HttpStatus.NOT_FOUND.value(),
                        "service provider '" + name + "' is not found!", null)))
                .flatMapMany(ServiceProvider::getServices);
    }

}
