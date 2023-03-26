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

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class WebInstancePayload {

    private StatusInfo statusInfo;

    // V1
    private String name;
    private String serviceUrl;
    private String managementUrl;
    private Map<String, String> metadata;

    // V2
    private Registration registration;
    private Map<String, String> tags;

    public String getName() {
        return registration == null ? name : registration.getName();
    }

    public String getServiceUrl() {
        return registration == null ? serviceUrl : registration.getServiceUrl();
    }

    public String getManagementUrl() {
        return registration == null ? managementUrl : registration.getManagementUrl();
    }

    public Map<String, String> getMetadata() {
        return registration == null ? orEmptyMap(metadata) : orEmptyMap(registration.getMetadata());
    }

    public Map<String, String> getTags() {
        return orEmptyMap(tags);
    }

    private static Map<String, String> orEmptyMap(Map<String, String> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    @Getter
    @Setter
    public static class Registration {

        private String name;
        private String serviceUrl;
        private String managementUrl;
        private Map<String, String> metadata;

    }

    @Getter
    @Setter
    public static class StatusInfo {

        private String status;

    }

}
