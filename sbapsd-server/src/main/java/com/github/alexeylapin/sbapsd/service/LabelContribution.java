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
package com.github.alexeylapin.sbapsd.service;

import com.github.alexeylapin.sbapsd.model.Instance;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Context for {@link LabelContributor}
 */
@Getter
public class LabelContribution {

    private final String serviceProviderName;
    private final Map<String, String> staticLabels;
    private final String appName;
    private final List<Instance> instances;

    @Builder
    public LabelContribution(String serviceProviderName,
                             Map<String, String> staticLabels,
                             String appName,
                             List<Instance> instances) {
        this.serviceProviderName = Validate.notNull(serviceProviderName, "serviceProviderName must not be null");
        this.staticLabels = Validate.notNull(staticLabels, "staticLabels must not be null");
        this.appName = Validate.notNull(appName, "appName must not be null");
        this.instances = Validate.notNull(instances, "instances must not be null");
    }

}
