plugins {
    id("java-library")
}

dependencies {
    api(project(":sbapsd-core"))
    implementation("de.codecentric:spring-boot-admin-server:2.7.10")
    implementation("org.springframework.boot:spring-boot-starter:2.7.7")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}