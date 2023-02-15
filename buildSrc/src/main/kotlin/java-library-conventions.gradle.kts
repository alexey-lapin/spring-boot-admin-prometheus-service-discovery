plugins {
    `java-library`
    `maven-publish`
    `signing`
    id("java-base-conventions")
}

java{
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "OSSRH"
            val snapshotUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
            val stagingUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            setUrl(if (version.toString().endsWith("SNAPSHOT")) snapshotUrl else stagingUrl)
            credentials {
                username = System.getenv("OSSRH_USER") ?: return@credentials
                password = System.getenv("OSSRH_PASSWORD") ?: return@credentials
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
