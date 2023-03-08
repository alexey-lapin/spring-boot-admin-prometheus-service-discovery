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
        private Map<String, String> metadata;

    }

    @Getter
    @Setter
    public static class StatusInfo {

        private String status;

    }

}
