package com.github.alexeylapin.sbapsd.testing;

import com.github.alexeylapin.sbapsd.model.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@WebFluxTest
public class WebTest {

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
        client.get()
                .uri("/service-discovery/prometheus/s1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(3)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__app_name"))
                                .containsExactlyInAnyOrder("app-1", "app-2", "app-3"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("no-filters")
                ));
    }

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithStatusFilter() {
        client.get()
                .uri("/service-discovery/prometheus/s2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(2)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__app_name"))
                                .containsExactlyInAnyOrder("app-1", "app-3"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("filter-status")
                ));
    }

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithAppNameFilter() {
        client.get()
                .uri("/service-discovery/prometheus/s3")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(1)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__app_name"))
                                .containsExactlyInAnyOrder("app-1"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("filter-app-name"),
                        () -> assertThat(list.get(0).getTargets()).hasSize(2).doesNotContainNull()
                ));
    }

    @Test
    void should_returnCorrectServiceList_when_requestingEndpointWithAppNameAndStatusFilter() {
        client.get()
                .uri("/service-discovery/prometheus/s4")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(1)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__app_name"))
                                .containsExactlyInAnyOrder("app-1"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("filter-app-name-status"),
                        () -> assertThat(list.get(0).getTargets()).hasSize(1).doesNotContainNull()
                ));
    }

}
