plugins {
    id("java-base-conventions")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":sbapsd-starter"))

    if (isSBAVersion3()) {
        implementation("de.codecentric:spring-boot-admin-starter-server:${getSBAVersion()}")
        implementation("de.codecentric:spring-boot-admin-starter-client:${getSBAVersion()}")
    }
}

tasks.getByName<Test>("test") {
    onlyIf { isSBAVersion3() }
}

tasks.withType<JavaCompile>() {
    onlyIf { isSBAVersion3() }
}

tasks.create<JavaExec>("runV3SBAServer") {
    onlyIf { isSBAVersion3() }
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.github.alexeylapin.sbapsd.testing.V3SBATesting")
    systemProperty("sba.server.port", findProperty("sba.server.port") ?: "8083")
}