package com.github.alexeylapin.sbapsd.testing;

import com.github.alexeylapin.sbapsd.config.EnableAdminServerServiceDiscovery;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAdminServer
@EnableAdminServerServiceDiscovery
@EnableAutoConfiguration
@SpringBootConfiguration
public class V3SBAServer {
}
