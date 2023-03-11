plugins {
    id("sbapsd.java-library-conventions")
}

description = "Spring Boot Admin Prometheus Service Discovery Server"

dependencies {
    annotationProcessor(platform(libs.spring.boot.v2.bom.get()))
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly(platform(libs.spring.boot.v2.bom.get()))
    compileOnly("de.codecentric:spring-boot-admin-server:${libs.versions.sba.v2.get()}")
    compileOnly("org.projectlombok:lombok")
    compileOnly("org.springframework.boot:spring-boot-starter-web")

    testImplementation(platform(libs.spring.boot.v2.bom.get()))
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.springframework.boot:spring-boot-starter-web")
}
