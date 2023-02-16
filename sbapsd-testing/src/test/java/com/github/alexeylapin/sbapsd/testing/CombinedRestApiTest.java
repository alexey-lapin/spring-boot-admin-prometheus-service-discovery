package com.github.alexeylapin.sbapsd.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

@EnabledIfSystemProperty(named = "sba.version", matches = "^[2,3]\\.\\d+\\.\\d+$")
@WebFluxTest
public class CombinedRestApiTest {

    @Configuration(proxyBeanMethods = false)
    public static class WebTestClientConfig {

        @Bean
        public WebTestClient webTestClient(@Value("${sba.server.port}") String sbaServerPort) {
            return WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + sbaServerPort)
                    .build();
        }

    }

    @Autowired
    private WebTestClient client;

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithNoFilters() {
        RestApiTestSupport.assertCallWithNoFilters(client, "/service-discovery/prometheus/s1");
    }

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithStatusFilter() {
        RestApiTestSupport.assertCallWithStatusFilter(client, "/service-discovery/prometheus/s2");
    }

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithAppNameFilter() {
        RestApiTestSupport.assertCallWithAppNameFilter(client, "/service-discovery/prometheus/s3");
    }

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithAppNameAndStatusFilter() {
        RestApiTestSupport.assertCallWithAppNameAndStatusFilter(client, "/service-discovery/prometheus/s4");
    }

}
