import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask

plugins {
    id("java")
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.18"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("sbapsd")
        }
    }
}

dependencies {
    implementation(project(":sbapsd-server"))
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

val writeVersionFile by tasks.registering {
    doLast {
        val outputDirectory = tasks.getByName<BuildNativeImageTask>("nativeCompile").outputDirectory
        outputDirectory.get().asFile.mkdirs()
        outputDirectory.file("version.txt")
            .get().asFile
            .writeText(project.version.toString())
    }
}

tasks.getByName("nativeCompile") {
    finalizedBy(writeVersionFile)
}
