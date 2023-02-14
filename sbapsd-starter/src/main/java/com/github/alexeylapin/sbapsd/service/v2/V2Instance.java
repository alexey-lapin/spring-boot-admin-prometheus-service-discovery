package com.github.alexeylapin.sbapsd.service.v2;

import lombok.Data;

import java.util.Map;

@Data
public class V2Instance {

    private Registration registration;
    private StatusInfo statusInfo;
    private Map<String, String> tags;

    @Data
    public static class Registration {

        private String name;
        private String serviceUrl;
        private Map<String, String> metadata;

    }

    @Data
    public static class StatusInfo {

        private String status;

    }

}
