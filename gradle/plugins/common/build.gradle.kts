plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.foojay.resolver.plugin)
    implementation(libs.spotless.plugin)
}
