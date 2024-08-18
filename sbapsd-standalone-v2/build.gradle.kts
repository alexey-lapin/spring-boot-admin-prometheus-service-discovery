plugins {
    id("java")
    alias(libs.plugins.spring.boot.v2)
    alias(libs.plugins.spring.deps)
    id("sbapsd.project-conventions")
    id("sbapsd.spotless-conventions")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":sbapsd-server"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
}
