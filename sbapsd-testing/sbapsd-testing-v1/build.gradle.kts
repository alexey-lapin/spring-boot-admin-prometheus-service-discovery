plugins {
    id("sbapsd.java-base-conventions")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":sbapsd-server"))

    if (isSBAVersion1()) {
        implementation("de.codecentric:spring-boot-admin-server:${getSBAVersion()}")
        implementation("de.codecentric:spring-boot-admin-starter-client:${getSBAVersion()}")
    }
}

tasks.getByName<Test>("test") {
    onlyIf { isSBAVersion1() }
}

tasks.withType<JavaCompile>() {
    onlyIf { isSBAVersion1() }
}

tasks.create<JavaExec>("runV1SBAServer") {
    onlyIf { isSBAVersion1() }
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("com.github.alexeylapin.sbapsd.testing.V1SBATesting")
    systemProperty("sba.server.port", findProperty("sba.server.port") ?: "8081")
    javaLauncher = javaToolchains.launcherFor {
        languageVersion = JavaLanguageVersion.of(8)
    }
}
