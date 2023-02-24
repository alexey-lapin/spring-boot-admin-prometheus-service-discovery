dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "spring-boot-admin-prometheus-service-discovery"

include("sbapsd-server")
include("sbapsd-testing")
if (JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    include("sbapsd-standalone")
}

include("sbapsd-testing:sbapsd-testing-v1")
findProject(":sbapsd-testing:sbapsd-testing-v1")?.name = "sbapsd-testing-v1"
include("sbapsd-testing:sbapsd-testing-v2")
findProject(":sbapsd-testing:sbapsd-testing-v2")?.name = "sbapsd-testing-v2"
include("sbapsd-testing:sbapsd-testing-v3")
findProject(":sbapsd-testing:sbapsd-testing-v3")?.name = "sbapsd-testing-v3"
