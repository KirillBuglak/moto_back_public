plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSpring)
    alias(libs.plugins.springBoot)
    alias(libs.plugins.dependencyManagement)
}

version = "1.0-SNAPSHOT"

dependencies {
    // COMMONS
    implementation(project(":commons"))
    // APACHE
    implementation(libs.bundles.apacheBundle)
    // MAIL
    implementation(libs.mail)
}

kotlin {
    jvmToolchain(21)
}