plugins {
    id("java")
}

dependencies {
    implementation("de.codecentric:spring-boot-admin-starter-server:2.7.10")
    implementation("de.codecentric:spring-boot-admin-starter-client:2.7.10")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}