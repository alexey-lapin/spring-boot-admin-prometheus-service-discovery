plugins {
    id("java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

val ver = "3.0.0"

dependencies {
    implementation("de.codecentric:spring-boot-admin-starter-server:${ver}")
    implementation("de.codecentric:spring-boot-admin-starter-client:${ver}")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
