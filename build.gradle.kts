plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72" apply false
    id("io.gitlab.arturbosch.detekt") version "1.10.0" apply false
    id("org.flywaydb.flyway") version "6.4.4" apply false
    `maven-publish`
}

subprojects {
    group = "com.rjdesenvolvimento.aries"
    version = "0.0.1"

    repositories {
        jcenter()
        maven(url = "https://kotlin.bintray.com/ktor") {
            name = "bintray-ktor"
        }
        maven(url = "http://packages.confluent.io/maven/")
        maven(url = "https://jitpack.io")
    }


    // configure language level

//    logger.lifecycle("configuring ${project.displayName}")

    afterEvaluate {
        if(project.plugins.hasPlugin("org.jetbrains.kotlin.jvm")) {
//            logger.lifecycle("configuring kotlin on ${project.displayName}")

//            // configure source dirs
//            apply(plugin = "org.jetbrains.kotlin.jvm")
//            kotlin.sourceSets["main"].kotlin.srcDirs("src")
//            kotlin.sourceSets["test"].kotlin.srcDirs("test")
//
//            sourceSets["main"].resources.srcDirs("resources")
//            sourceSets["test"].resources.srcDirs("testresources")

            tasks {
                compileKotlin { kotlinOptions.jvmTarget = "11" }
                compileTestKotlin { kotlinOptions.jvmTarget = "11" }
                withType<io.gitlab.arturbosch.detekt.Detekt> { this.jvmTarget = "11" }
                test {
                    useJUnitPlatform()
                    testLogging {
                        events("passed", "skipped", "failed")
                    }
                }
            }

            // configure publishing
            publishing {
                publications {
                    create<MavenPublication>("mavenKotlin") {
                        from(components["kotlin"])
                        pom {
                            licenses {
                                license {
                                    name.set("The Apache License, Version 2.0")
                                    url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                                }
                            }
                        }
                    }
                }
            }
        }

//        if(project.plugins.hasPlugin("io.gitlab.arturbosch.detekt")) {
//            logger.lifecycle("configuring detekt on ${project.displayName}")
//
//        }
    }
}