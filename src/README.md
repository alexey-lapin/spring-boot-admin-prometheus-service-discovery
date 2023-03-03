## SBAPSD
### Spring Boot Admin Prometheus Service Discovery

### Usage
#### As a library
```kotlin
implementation("com.github.alexey-lapin.sbapsd:sbapsd-server:@version@")
```
```xml
<dependency>
    <groupId>com.github.alexey-lapin.sbapsd</groupId>
    <artifactId>sbapsd-server</artifactId>
    <version>@version@</version>
</dependency>
```

#### As a standalone app
Grab a jar from the [releases page](https://github.com/alexey-lapin/spring-boot-admin-prometheus-service-discovery/releases/latest):
- v2 is based on Spring Boot 2 and requires Java 8
- v3 is based on Spring Boot 3 and requires Java 17

and run it like so:
```shell
java -jar sbapsd-standalone-v2-@version@.jar
```
or
```shell
java -jar sbapsd-standalone-v3-@version@.jar
```

Standalone app is also available as experimental native binaries. 

#### Configuration
```yml
sbapsd:
  servers:
    server-1:
      type: web
      params:
        url: http://localhost:8090
```

#### Prometheus
```yml
scrape_configs:
  - job_name: "spring"
    http_sd_configs:
      - url: http://localhost:8080/service-discovery/prometheus/server-1
    relabel_configs:
      - source_labels: [__meta_discovery_actuator_path]
        target_label: __metrics_path__
        replacement: $1/prometheus
      - source_labels: [__meta_discovery_app_name]
        target_label: app
```
