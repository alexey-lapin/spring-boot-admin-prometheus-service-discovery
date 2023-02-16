package com.github.alexeylapin.sbapsd.service.v1;

import lombok.Data;

import java.util.Map;

@Data
public class V1Application {

    private String name;
    private String serviceUrl;
    private StatusInfo statusInfo;
    private Map<String, String> metadata;

    @Data
    public static class StatusInfo {

        private String status;

    }

}
