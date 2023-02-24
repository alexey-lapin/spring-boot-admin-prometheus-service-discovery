import net.researchgate.release.ReleaseExtension
import net.researchgate.release.tasks.PreTagCommit
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("net.researchgate.release") version "3.0.2"
}

release {
    tagTemplate.set("v${'$'}version")
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
