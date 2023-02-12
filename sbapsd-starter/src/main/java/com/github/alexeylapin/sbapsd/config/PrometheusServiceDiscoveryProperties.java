package com.github.alexeylapin.sbapsd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "sbapsd")
public class PrometheusServiceDiscoveryProperties {

    private String path;
    private Map<String, InstanceProviderDef> servers = new HashMap<>();

}
