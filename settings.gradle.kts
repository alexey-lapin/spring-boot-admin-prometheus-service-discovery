dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "spring-boot-admin-prometheus-sd"
include("sbapsd-core")
include("sbapsd-starter")
include("sbapsd-app")
include("sbapsd-testing")
