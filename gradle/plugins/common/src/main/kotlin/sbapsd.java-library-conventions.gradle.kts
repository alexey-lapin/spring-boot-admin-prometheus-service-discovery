plugins {
    `java-library`
    `maven-publish`
    `signing`
    id("sbapsd.java-base-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set(project.name)
                description.set(provider { project.description })
                url.set("https://github.com/alexey-lapin/spring-boot-admin-prometheus-service-discovery")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("alexey-lapin")
                        name.set("Alexey Lapin")
                        email.set("alexey-lapin@protonmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:alexey-lapin/spring-boot-admin-prometheus-service-discovery.git")
                    developerConnection.set("scm:git:git@github.com:alexey-lapin/spring-boot-admin-prometheus-service-discovery.git")
                    url.set("https://github.com/alexey-lapin/spring-boot-admin-prometheus-service-discovery")
                }
            }
        }
    }
}

signing {
    val key = System.getenv("SIGNING_KEY") ?: return@signing
    val password = System.getenv("SIGNING_PASSWORD") ?: return@signing
    val publishing: PublishingExtension by project

    useInMemoryPgpKeys(key, password)
    sign(publishing.publications)
}
