import de.fayard.dependencies.bootstrapRefreshVersionsAndDependencies

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies.classpath("de.fayard:dependencies:0.5.8")
}

plugins {
    id("com.gradle.enterprise").version("3.1.1")
}

bootstrapRefreshVersionsAndDependencies(
        listOf(rootDir.resolve("dependencies-rules.txt").readText())
)

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
//        publishAlwaysIf(true)
    }
}

rootProject.name = "proof-of-concept"

include(":access:access.consumer")
include(":access:access.orchestration")

include(":commons:commons.consumer")
include(":commons:commons.elasticsearch")
include(":commons:commons.kafka")
include(":commons:commons.orchestration")
include(":commons:commons.patterns")
include(":commons:commons.test")
