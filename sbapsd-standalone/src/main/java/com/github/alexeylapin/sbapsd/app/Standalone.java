package com.github.alexeylapin.sbapsd.app;

import com.github.alexeylapin.sbapsd.config.EnableAdminServerServiceDiscovery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServerServiceDiscovery
public class Standalone {

    public static void main(String[] args) {
        SpringApplication.run(Standalone.class, args);
    }

}
