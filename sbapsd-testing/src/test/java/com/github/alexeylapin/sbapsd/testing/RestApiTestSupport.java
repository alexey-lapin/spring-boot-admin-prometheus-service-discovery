package com.github.alexeylapin.sbapsd.testing;

import com.github.alexeylapin.sbapsd.model.Service;
import com.github.alexeylapin.sbapsd.service.LabelContributors;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public abstract class RestApiTestSupport {

    static void assertCallWithNoFilters(WebTestClient client, String url) {
        client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(3)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels()
                                        .get(LabelContributors.AppNameLabelContributor.LABEL_APP_NAME))
                                .containsExactlyInAnyOrder("app-1", "app-2", "app-3"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("no-filters")
                ));
    }

    static void assertCallWithStatusFilter(WebTestClient client, String url) {
        client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(2)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels()
                                        .get(LabelContributors.AppNameLabelContributor.LABEL_APP_NAME))
                                .containsExactlyInAnyOrder("app-1", "app-3"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("filter-status")
                ));
    }

    static void assertCallWithAppNameFilter(WebTestClient client, String url) {
        client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(1)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels()
                                        .get(LabelContributors.AppNameLabelContributor.LABEL_APP_NAME))
                                .containsExactlyInAnyOrder("app-1"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("filter-app-name"),
                        () -> assertThat(list.get(0).getTargets()).hasSize(2).doesNotContainNull()
                ));
    }

    static void assertCallWithAppNameAndStatusFilter(WebTestClient client, String url) {
        client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Service.class)
                .hasSize(1)
                .value(list -> assertAll(
                        () -> assertThat(list).extracting(item -> item.getLabels()
                                        .get(LabelContributors.AppNameLabelContributor.LABEL_APP_NAME))
                                .containsExactlyInAnyOrder("app-1"),
                        () -> assertThat(list).extracting(item -> item.getLabels().get("__test"))
                                .containsOnly("filter-app-name-status"),
                        () -> assertThat(list.get(0).getTargets()).hasSize(1).doesNotContainNull()
                ));
    }

}
