plugins {
    id("java-library-conventions")
}

description = "Spring Boot Admin Prometheus Service Discovery Server"

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.26")
    compileOnly("org.projectlombok:lombok:1.18.26")

    compileOnly("de.codecentric:spring-boot-admin-server:2.7.10")
    compileOnly("org.springframework.boot:spring-boot-starter-web:2.7.7")

    testRuntimeOnly("org.springframework.boot:spring-boot-starter-web:2.7.7")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.assertj:assertj-core:3.24.2")
}
