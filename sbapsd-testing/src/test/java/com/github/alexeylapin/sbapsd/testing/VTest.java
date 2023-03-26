//package com.github.alexeylapin.sbapsd.testing;
//
//import com.github.alexeylapin.sbapsd.config.EnableAdminServerServiceDiscovery;
//import com.github.alexeylapin.sbapsd.config.ServiceDiscoveryAutoConfiguration;
//import de.codecentric.boot.admin.server.config.EnableAdminServer;
//import de.codecentric.boot.admin.server.services.ApplicationRegistry;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.context.logging.LoggingApplicationListener;
//import org.springframework.boot.test.context.runner.ReactiveWebApplicationContextRunner;
//import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Disabled
//public class VTest {
//
//    @Test
//    void name() {
//        new ReactiveWebApplicationContextRunner(() -> {
//            AnnotationConfigReactiveWebApplicationContext context = new AnnotationConfigReactiveWebApplicationContext();
//            context.addApplicationListener(new LoggingApplicationListener());
//            return context;
//        })
//                .withUserConfiguration(A.class)
////                .withPropertyValues("sbapsd.providers.s1.type=registry")
//                .withPropertyValues("server.port=-1")
//                .withPropertyValues("sbapsd.providers.s2.type=web")
//                .withPropertyValues("sbapsd.providers.s2.params.url=http://localhost:8080")
////                .with
//                .run(context -> {
//                    assertThat(context).hasSingleBean(ApplicationRegistry.class);
////                    WebClient webClient = WebClient.create("localhost:8080");
////                    String property = context.getEnvironment().getProperty("local.server.port");
////                    WebTestClient build = WebTestClient.bindToServer()
////                            .baseUrl("http://localhost:8080")
////                            .build();
////                    build.get()
////                            .uri("/service-discovery/prometheus/s2")
////                            .accept(MediaType.APPLICATION_JSON)
////                            .exchange()
////                            .expectStatus()
////                            .is2xxSuccessful();
//                });
//    }
//
//    @EnableAdminServer
//    @EnableAdminServerServiceDiscovery
//    @SpringBootApplication
//    public static class A {
//
//    }
//
//}
