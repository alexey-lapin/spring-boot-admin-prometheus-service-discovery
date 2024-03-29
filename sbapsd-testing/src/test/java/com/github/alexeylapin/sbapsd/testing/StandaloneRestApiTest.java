package com.github.alexeylapin.sbapsd.testing;

import com.github.alexeylapin.sbapsd.config.EnableAdminServerServiceDiscovery;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "sbapsd.providers.v1-s1.type=web",
                "sbapsd.providers.v1-s1.params.url=http://localhost:${sba.server.port}/api/applications",
                "sbapsd.providers.v1-s1.labels.__test=no-filters",
                "sbapsd.providers.v1-s2.type=web",
                "sbapsd.providers.v1-s2.params.url=http://localhost:${sba.server.port}/api/applications",
                "sbapsd.providers.v1-s2.labels.__test=filter-status",
                "sbapsd.providers.v1-s2.filters[0].type=status",
                "sbapsd.providers.v1-s2.filters[0].params.value=UP",
                "sbapsd.providers.v1-s3.type=web",
                "sbapsd.providers.v1-s3.params.url=http://localhost:${sba.server.port}/api/applications",
                "sbapsd.providers.v1-s3.labels.__test=filter-app-name",
                "sbapsd.providers.v1-s3.filters[0].type=app-name",
                "sbapsd.providers.v1-s3.filters[0].params.value=app-1",
                "sbapsd.providers.v1-s4.type=web",
                "sbapsd.providers.v1-s4.params.url=http://localhost:${sba.server.port}/api/applications",
                "sbapsd.providers.v1-s4.labels.__test=filter-app-name-status",
                "sbapsd.providers.v1-s4.filters[0].type=app-name",
                "sbapsd.providers.v1-s4.filters[0].params.value=app-1",
                "sbapsd.providers.v1-s4.filters[1].type=status",
                "sbapsd.providers.v1-s4.filters[1].params.value=UP",
                "sbapsd.providers.v2-s1.type=web",
                "sbapsd.providers.v2-s1.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v2-s1.labels.__test=no-filters",
                "sbapsd.providers.v2-s2.type=web",
                "sbapsd.providers.v2-s2.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v2-s2.labels.__test=filter-status",
                "sbapsd.providers.v2-s2.filters[0].type=status",
                "sbapsd.providers.v2-s2.filters[0].params.value=UP",
                "sbapsd.providers.v2-s3.type=web",
                "sbapsd.providers.v2-s3.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v2-s3.labels.__test=filter-app-name",
                "sbapsd.providers.v2-s3.filters[0].type=app-name",
                "sbapsd.providers.v2-s3.filters[0].params.value=app-1",
                "sbapsd.providers.v2-s4.type=web",
                "sbapsd.providers.v2-s4.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v2-s4.labels.__test=filter-app-name-status",
                "sbapsd.providers.v2-s4.filters[0].type=app-name",
                "sbapsd.providers.v2-s4.filters[0].params.value=app-1",
                "sbapsd.providers.v2-s4.filters[1].type=status",
                "sbapsd.providers.v2-s4.filters[1].params.value=UP",
                "sbapsd.providers.v3-s1.type=web",
                "sbapsd.providers.v3-s1.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v3-s1.labels.__test=no-filters",
                "sbapsd.providers.v3-s2.type=web",
                "sbapsd.providers.v3-s2.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v3-s2.labels.__test=filter-status",
                "sbapsd.providers.v3-s2.filters[0].type=status",
                "sbapsd.providers.v3-s2.filters[0].params.value=UP",
                "sbapsd.providers.v3-s3.type=web",
                "sbapsd.providers.v3-s3.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v3-s3.labels.__test=filter-app-name",
                "sbapsd.providers.v3-s3.filters[0].type=app-name",
                "sbapsd.providers.v3-s3.filters[0].params.value=app-1",
                "sbapsd.providers.v3-s4.type=web",
                "sbapsd.providers.v3-s4.params.url=http://localhost:${sba.server.port}/instances",
                "sbapsd.providers.v3-s4.labels.__test=filter-app-name-status",
                "sbapsd.providers.v3-s4.filters[0].type=app-name",
                "sbapsd.providers.v3-s4.filters[0].params.value=app-1",
                "sbapsd.providers.v3-s4.filters[1].type=status",
                "sbapsd.providers.v3-s4.filters[1].params.value=UP",
        })
public class StandaloneRestApiTest {

    @EnableAdminServerServiceDiscovery
    @SpringBootApplication
    public static class WebTestClientConfig {

        @Lazy
        @Bean
        public WebTestClient webTestClient(@Value("${local.server.port}") String port) {
            return WebTestClient.bindToServer()
                    .baseUrl("http://localhost:" + port)
                    .build();
        }

    }

    @Autowired
    private WebTestClient client;


    @EnabledIfSystemProperty(named = "sba.version", matches = "^1\\.\\d+\\.\\d+$")
    @Nested
    class V1Test {

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithNoFilters() {
            RestApiTestSupport.assertCallWithNoFilters(client, "/service-discovery/prometheus/v1-s1");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithStatusFilter() {
            RestApiTestSupport.assertCallWithStatusFilter(client, "/service-discovery/prometheus/v1-s2");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithAppNameFilter() {
            RestApiTestSupport.assertCallWithAppNameFilter(client, "/service-discovery/prometheus/v1-s3");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithAppNameAndStatusFilter() {
            RestApiTestSupport.assertCallWithAppNameAndStatusFilter(client, "/service-discovery/prometheus/v1-s4");
        }

    }

    @EnabledIfSystemProperty(named = "sba.version", matches = "^2\\.\\d+\\.\\d+$")
    @Nested
    class V2Test {

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithNoFilters() {
            RestApiTestSupport.assertCallWithNoFilters(client, "/service-discovery/prometheus/v2-s1");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithStatusFilter() {
            RestApiTestSupport.assertCallWithStatusFilter(client, "/service-discovery/prometheus/v2-s2");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithAppNameFilter() {
            RestApiTestSupport.assertCallWithAppNameFilter(client, "/service-discovery/prometheus/v2-s3");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithAppNameAndStatusFilter() {
            RestApiTestSupport.assertCallWithAppNameAndStatusFilter(client, "/service-discovery/prometheus/v2-s4");
        }

    }

    @EnabledIfSystemProperty(named = "sba.version", matches = "^3\\.\\d+\\.\\d+$")
    @Nested
    class V3Test {

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithNoFilters() {
            RestApiTestSupport.assertCallWithNoFilters(client, "/service-discovery/prometheus/v3-s1");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithStatusFilter() {
            RestApiTestSupport.assertCallWithStatusFilter(client, "/service-discovery/prometheus/v3-s2");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithAppNameFilter() {
            RestApiTestSupport.assertCallWithAppNameFilter(client, "/service-discovery/prometheus/v3-s3");
        }

        @Test
        void should_returnCorrectServiceList_when_requestingEndpointWithAppNameAndStatusFilter() {
            RestApiTestSupport.assertCallWithAppNameAndStatusFilter(client, "/service-discovery/prometheus/v3-s4");
        }

    }

}
