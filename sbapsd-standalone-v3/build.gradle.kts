import org.graalvm.buildtools.gradle.tasks.BuildNativeImageTask
import org.gradle.internal.os.OperatingSystem

plugins {
    id("java")
    alias(libs.plugins.spring.boot.v3)
    alias(libs.plugins.spring.deps)
    alias(libs.plugins.graalvm)
    id("sbapsd.spotless-conventions")
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
    implementation("io.micrometer:micrometer-registry-prometheus")
}

val writeArtifactFile by tasks.registering {
    doLast {
        val outputDirectory = tasks.named<BuildNativeImageTask>("nativeCompile").get().outputDirectory
        outputDirectory.get().asFile.mkdirs()
        outputDirectory.file("gradle-artifact.txt")
            .get().asFile
            .writeText("${project.name}-${project.version}-${platform()}")
    }
}

tasks.named("nativeCompile") {
    finalizedBy(writeArtifactFile)
}

fun platform(): String {
    val os = OperatingSystem.current()
    val arc = System.getProperty("os.arch")
    return when {
        OperatingSystem.current().isWindows -> "windows-${arc}"
        OperatingSystem.current().isLinux -> "linux-${arc}"
        OperatingSystem.current().isMacOsX -> "darwin-${arc}"
        else -> os.nativePrefix
    }
}

