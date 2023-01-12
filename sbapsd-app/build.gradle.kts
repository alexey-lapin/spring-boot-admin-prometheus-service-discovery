plugins {
    id("java")
}

dependencies {
    implementation(project(":sbapsd-starter"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.7")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}