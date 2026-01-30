subprojects {
    group = "kb"

    repositories {
        mavenCentral()
    }

    plugins.withId("org.jetbrains.kotlin.plugin.spring") {
        dependencies {
            // LOMBOK
            add("compileOnly", libs.lombok)
            add("annotationProcessor", libs.lombok)
            // POSTGRES
            add("runtimeOnly", libs.postgres)
            // KAFKA
            add("implementation", libs.bundles.kafkaBundle)
            // TEST
            add("testImplementation", libs.bundles.testBundle)
            add("testRuntimeOnly", libs.testLauncher)
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") {
        dependencies {
            // JACKSON
            add("implementation", libs.bundles.jacksonBundle)
            // KOTLIN
            add("implementation", libs.bundles.kotlinBundle)
            // POSTGRES
            add("implementation", libs.bundles.postgresR2dbc)
        }
    }


    tasks.withType<Test> {
        useJUnitPlatform()
    }
}