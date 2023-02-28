dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        includeBuild("gradle/plugins")
        gradlePluginPortal()
    }
}

rootProject.name = "spring-boot-admin-prometheus-service-discovery"

include("sbapsd-server")
include("sbapsd-testing")
include("sbapsd-standalone-v2")
if (JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    include("sbapsd-standalone-v3")
}

include("sbapsd-testing:sbapsd-testing-v1")
findProject(":sbapsd-testing:sbapsd-testing-v1")?.name = "sbapsd-testing-v1"
include("sbapsd-testing:sbapsd-testing-v2")
findProject(":sbapsd-testing:sbapsd-testing-v2")?.name = "sbapsd-testing-v2"
include("sbapsd-testing:sbapsd-testing-v3")
findProject(":sbapsd-testing:sbapsd-testing-v3")?.name = "sbapsd-testing-v3"
