import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask

plugins {
    id("java")
    alias(libs.plugins.spring.boot.v3)
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.graalvm)
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
