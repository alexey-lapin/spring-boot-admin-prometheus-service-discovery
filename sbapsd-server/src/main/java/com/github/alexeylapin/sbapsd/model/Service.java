package com.github.alexeylapin.sbapsd.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a collection of instances in Prometheus SD format
 * @see <a href="https://prometheus.io/docs/prometheus/latest/http_sd">prometheus http_sd</a>
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Data
public class Service {

    private List<String> targets;
    private Map<String, String> labels;

}
