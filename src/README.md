# SBAPSD
## Spring Boot Admin Prometheus Service Discovery

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.alexey-lapin.sbapsd/sbapsd-server/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.alexey-lapin.sbapsd/sbapsd-server/)

This project provides the way to expose applications registered
in [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)
for [Prometheus](https://prometheus.io/) [http service discovery](https://prometheus.io/docs/prometheus/latest/configuration/configuration/#http_sd_config).

The service discovery functionality can be enabled by applying `@EnableAdminServerServiceDiscovery` annotation.
One option is to use the library together with Spring Boot Admin server in the same app. This way the
SBA's `instance registry`based provider is available. Another option is to utilize this library separately, 
then only REST based provider is configured.

```java
@SpringBootApplication
@EnableAdminServer
@EnableAdminServerServiceDiscovery
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
```

The library is tested with Spring Boot Admin v1, v2, v3.

| type     | v1  | v2  | v3  |
|----------|-----|-----|-----|
| registry |     | ✔   | ✔   |
| web      | ✔   | ✔   | ✔   |


## Usage
### As a library

1. Add dependency
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
2. Put the `@EnableAdminServerServiceDiscovery` annotation
3. Add config props - see Configuration section
4. Customize autoconfigured beans if necessary (see `ServiceDiscoveryAutoConfiguration` class) 

### As a standalone app
Grab a jar from
the [releases page](https://github.com/alexey-lapin/spring-boot-admin-prometheus-service-discovery/releases/latest):

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

### Configuration
Example:
```yml
sbapsd:
  servers:
    server-1:
      type: web
      params:
        url: http://localhost:8091
      labels:
        static-label-1: val-1
    server-2:
      type: web
      params:
        url: http://localhost:8092
      filters:
        - type: app-name
          params:
            value: app-.*$
    server-3:
      type: registry
      labels:
        static-label-2: val2
      filters:
        - type: status
          params:
            value: UP,DOWN
```

#### sbapsd
Root config

| key     | type   |
|---------|--------|
| servers | server |

#### server
Configures the way of how to obtain instances either directly from instance **registry** or via **web** 

| key     | type            |
|---------|-----------------|
| type*   | web or registry |
| params  | map             |
| labels  | map             |
| filters | list            |

#### web type params
Configures rest connectivity

| key      | type   |
|----------|--------|
| url*     | url    |
| username | string |
| password | string |
| insecure | bool   |



### Prometheus
```yml
scrape_configs:
  - job_name: "spring"
    http_sd_configs:
      - url: http://localhost:8080/service-discovery/prometheus/server-1
    relabel_configs:
      - source_labels: [ __meta_discovery_actuator_path ]
        target_label: __metrics_path__
        replacement: $1/prometheus
      - source_labels: [ __meta_discovery_app_name ]
        target_label: app
```
