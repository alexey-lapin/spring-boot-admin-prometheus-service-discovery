package com.github.alexeylapin.sbapsd.app;

import com.github.alexeylapin.sbapsd.config.EnableAdminServerServiceDiscovery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServerServiceDiscovery
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
