import net.researchgate.release.ReleaseExtension
import net.researchgate.release.tasks.PreTagCommit
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    alias(libs.plugins.release)
    alias(libs.plugins.nexus)
}

release {
    tagTemplate.set("v${'$'}version")
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("OSSRH_USER") ?: return@sonatype)
            password.set(System.getenv("OSSRH_PASSWORD") ?: return@sonatype)
        }
    }
}

val releaseUpdatableFiles = listOf<Pair<String, String>>(
//    Pair("src/README.md", ".")
)

val updateReleaseDependentFiles by tasks.registering(Copy::class) {
    releaseUpdatableFiles.forEach {
        from(it.first) {
            filter(ReplaceTokens::class, Pair("tokens", mapOf(Pair("version", version))))
            into(it.second)
        }
    }
    into(projectDir)
}

tasks.getByName("afterReleaseBuild") {
    dependsOn(updateReleaseDependentFiles)
}

tasks.getByName<PreTagCommit>("preTagCommit") {
    doFirst {
        val scmAdapter = project.extensions.getByName<ReleaseExtension>("release").scmAdapter
        releaseUpdatableFiles.forEach {
            scmAdapter.add(file(it.second))
        }
    }
}
