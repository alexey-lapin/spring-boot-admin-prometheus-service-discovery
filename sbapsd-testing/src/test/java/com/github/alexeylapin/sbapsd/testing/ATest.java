//package com.github.alexeylapin.sbapsd.testing;
//
//import com.github.alexeylapin.sbapsd.config.EnableAdminServerServiceDiscovery;
//import com.github.alexeylapin.sbapsd.model.Service;
//import de.codecentric.boot.admin.server.config.EnableAdminServer;
//import de.codecentric.boot.admin.server.domain.values.InstanceId;
//import de.codecentric.boot.admin.server.domain.values.Registration;
//import de.codecentric.boot.admin.server.services.InstanceRegistry;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
//        properties = {
//                "sbapsd.servers.s1.type=registry-v2",
//                "sbapsd.servers.s2.type=web-v2",
//                "sbapsd.servers.s2.params.url=http://localhost:8080",
//        })
//public class ATest {
//
//    @EnableAdminServer
//    @EnableAdminServerServiceDiscovery
//    @SpringBootApplication
//    public static class A {
//
//    }
//
//    @Value("${local.server.port}")
//    private String port;
//
//    @Test
//    void name(@Autowired InstanceRegistry instanceRegistry) {
//        InstanceId instanceId = instanceRegistry.register(Registration.builder()
//                        .name("app-1")
//                        .healthUrl("http://localhost:9000")
//                        .build())
//                .block();
//
//        WebTestClient build = WebTestClient.bindToServer()
//                .baseUrl("http://localhost:" + port)
//                .build();
//
//        build.get()
//                .uri("/service-discovery/prometheus/s1")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectBodyList(Service.class)
//                .hasSize(1);
//    }
//
//}
