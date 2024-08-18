import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    alias(libs.plugins.nexus)
    id("pl.allegro.tech.build.axion-release")
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("OSSRH_USER") ?: return@sonatype)
            password.set(System.getenv("OSSRH_PASSWORD") ?: return@sonatype)
        }
    }
}

val releaseUpdatableFiles = listOf(
    Pair("src/README.md", ".")
)

val updateReleaseDependentFiles by tasks.registering(Copy::class) {
    releaseUpdatableFiles.forEach {
        from(it.first) {
            filter(ReplaceTokens::class, Pair("tokens", mapOf(Pair("version", version))))
            into(it.second)
        }
    }
    into(projectDir)
    doNotTrackState("workaround")
}