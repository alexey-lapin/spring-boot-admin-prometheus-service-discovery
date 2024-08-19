import org.apache.tools.ant.filters.ReplaceTokens
import pl.allegro.tech.build.axion.release.domain.hooks.HookContext

plugins {
    alias(libs.plugins.nexus)
    id("sbapsd.project-conventions")
    id("pl.allegro.tech.build.axion-release")
}

scmVersion {
    hooks {
        pre { c: HookContext -> c.addCommitPattern("README.md") }
        pre("commit")
    }
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
    mustRunAfter("verifyRelease")
}

tasks.named("createRelease").configure {
    dependsOn(updateReleaseDependentFiles)
}