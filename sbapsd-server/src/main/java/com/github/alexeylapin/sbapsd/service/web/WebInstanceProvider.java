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
package com.github.alexeylapin.sbapsd.service.web;

import com.github.alexeylapin.sbapsd.model.Instance;
import com.github.alexeylapin.sbapsd.service.InstanceProvider;
import com.github.alexeylapin.sbapsd.service.Validate;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebInstanceProvider implements InstanceProvider {

    private final WebClient client;

    public WebInstanceProvider(WebClient client) {
        this.client = Validate.notNull(client, "client must not be null");
    }

    @Override
    public Flux<Instance> getInstances() {
        return client.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(response -> response.bodyToFlux(WebInstancePayload.class))
                .map(this::convert);
    }

    private Instance convert(WebInstancePayload payload) {
        return Instance.builder()
                .name(payload.getName())
                .serviceUrl(payload.getServiceUrl())
                .managementUrl(payload.getManagementUrl())
                .status(payload.getStatusInfo().getStatus())
                .metadata(payload.getMetadata())
                .tags(payload.getTags())
                .build();
    }

}
