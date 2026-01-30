plugins {
	alias(libs.plugins.kotlinJvm)
	alias(libs.plugins.kotlinSpring)
	alias(libs.plugins.springBoot)
	alias(libs.plugins.dependencyManagement)
}

version = "1.0-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

dependencies {
	// COMMONS
	implementation(project(":commons"))
	// SPRING
	implementation(libs.bundles.springImplBundle)
//	runtimeOnly(libs.bundles.springDevToolsBundle)
	// REDIS
	implementation(libs.bundles.redisBundle)
	// REACTOR
	implementation(libs.bundles.reactorBundle)
	// MONGO
	implementation(libs.bundles.mongoBundle)
	// SECURITY
	implementation(libs.security)
	// JWT
	implementation(libs.bundles.jjwtBundle)
	// MONITORING
	implementation(libs.bundles.monitoringBundle)
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}