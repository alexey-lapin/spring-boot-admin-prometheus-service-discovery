import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("java-library-conventions")
    alias(libs.plugins.spring.boot.v2) apply false
}

apply(plugin = "io.spring.dependency-management")

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

description = "Spring Boot Admin Prometheus Service Discovery Server"

dependencies {
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    compileOnly("de.codecentric:spring-boot-admin-server:${libs.versions.sba.v2.get()}")
    compileOnly("org.springframework.boot:spring-boot-starter-web")

    testRuntimeOnly("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
}
