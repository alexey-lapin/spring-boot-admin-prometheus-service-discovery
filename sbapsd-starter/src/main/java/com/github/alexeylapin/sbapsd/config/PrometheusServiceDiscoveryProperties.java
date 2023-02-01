package com.github.alexeylapin.sbapsd.config;

import com.github.alexeylapin.sbapsd.ServerAccessor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "sbapsd")
public class PrometheusServiceDiscoveryProperties {

    private String path;
    private Map<String, ServerAccessor> servers = new HashMap<>();

    @Data
    public static class ServerAccessorProperties {

        private URL url;

    }

}
