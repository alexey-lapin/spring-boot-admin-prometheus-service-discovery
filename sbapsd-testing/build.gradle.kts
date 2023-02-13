plugins {
    id("java-base-conventions")
}

val ver = findProperty("prop1") ?: "2.7.10"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation("de.codecentric:spring-boot-admin-starter-server:${ver}")
    implementation("de.codecentric:spring-boot-admin-starter-client:${ver}")

    implementation(project(":sbapsd-starter"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.8")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
}
