package com.github.alexeylapin.sbapsd.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Represents an application instance registered in Spring Boot Admin
 */
@Builder
@Data
public class Instance {

    private String name;
    private String status;
    private String serviceUrl;
    private String managementUrl;
    private Map<String, String> tags;
    private Map<String, String> metadata;

}
