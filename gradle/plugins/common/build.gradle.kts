plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.foojay.resolver.plugin)
    implementation(libs.release.plugin)
    implementation(libs.spotless.plugin)
}
