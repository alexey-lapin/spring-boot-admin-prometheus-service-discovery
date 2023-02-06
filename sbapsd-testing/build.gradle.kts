plugins {
    id("java")
}

dependencies {
    implementation("de.codecentric:spring-boot-admin-starter-server:1.5.7")
    implementation("de.codecentric:spring-boot-admin-starter-client:1.5.7")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}