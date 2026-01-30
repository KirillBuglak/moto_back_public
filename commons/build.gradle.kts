plugins {
    alias(libs.plugins.kotlinJvm)
}

version = "1.0-SNAPSHOT"

dependencies {
    // SPRING
    implementation(libs.bundles.springImplBundle)
}

kotlin {
    jvmToolchain(21)
}