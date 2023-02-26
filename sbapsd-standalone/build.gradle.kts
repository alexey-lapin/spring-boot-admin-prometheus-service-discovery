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
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

val writeArtifactFile by tasks.registering {
    doLast {
        val outputDirectory = tasks.getByName<BuildNativeImageTask>("nativeCompile").outputDirectory
        outputDirectory.get().asFile.mkdirs()
        outputDirectory.file("gradle-artifact.txt")
            .get().asFile
            .writeText("${project.name}-${project.version}")
    }
}

tasks.getByName("nativeCompile") {
    finalizedBy(writeArtifactFile)
}
