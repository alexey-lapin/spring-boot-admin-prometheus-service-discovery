import pl.allegro.tech.build.axion.release.domain.VersionConfig

plugins {
    id("pl.allegro.tech.build.axion-release") apply false
}

group = "com.github.alexey-lapin.sbapsd"
version = rootProject.extensions.getByType(VersionConfig::class).version