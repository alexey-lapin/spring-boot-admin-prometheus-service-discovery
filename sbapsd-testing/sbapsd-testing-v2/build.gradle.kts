plugins {
    id("sbapsd.java-base-conventions")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":sbapsd-server"))

    if (isSBAVersion2()) {
        implementation("de.codecentric:spring-boot-admin-starter-server:${getSBAVersion()}")
        implementation("de.codecentric:spring-boot-admin-starter-client:${getSBAVersion()}")
        implementation("io.micrometer:micrometer-registry-prometheus:${libs.versions.micrometer.get()}")
    }
}

tasks.getByName<Test>("test") {
    onlyIf { isSBAVersion2() }
}

tasks.withType<JavaCompile>() {
    onlyIf { isSBAVersion2() }
}

tasks.create<JavaExec>("runV2SBAServer") {
    onlyIf { isSBAVersion2() }
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.github.alexeylapin.sbapsd.testing.V2SBATesting")
    systemProperty("sba.server.port", findProperty("sba.server.port") ?: "8082")
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(8)
    }
}
