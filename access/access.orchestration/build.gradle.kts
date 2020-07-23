val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val airesCommonsOrchestrationVersion: String by project
val ariesCommonsKafkaVersion: String by project
val ariesCommonsTestVersion: String by project
val ariesCommonsElkVersion: String by project

plugins {
  application
  kotlin("jvm")
  kotlin("plugin.serialization")
  id("io.gitlab.arturbosch.detekt")
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

application {
  mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
  mavenLocal()
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
  maven { url = uri("https://jitpack.io") }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("io.ktor:ktor-server-netty:$ktorVersion")

  implementation("io.ktor:ktor-metrics:$ktorVersion")
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-auth:$ktorVersion")
  implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

//  implementation("com.rjdesenvolvimento.aries:commons.orchestration:airesCommonsOrchestrationVersion")
  implementation(project(":commons:commons.orchestration"))
//  implementation("com.rjdesenvolvimento.aries:commons.kafka:$ariesCommonsKafkaVersion")
  implementation(project(":commons:commons.kafka"))
//  implementation("com.rjdesenvolvimento.aries:commons.elasticsearch:$ariesCommonsElkVersion")
  implementation(project(":commons:commons.elasticsearch"))

  testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
//  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesCommonsTestVersion")
  testImplementation(project(":commons:commons.test"))
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

detekt {
  buildUponDefaultConfig = false
  input = files("src")
  config = files("resources/detekt.yml")
  debug = true
  ignoreFailures = false
  reports {
    xml {
      enabled = true
      destination = file("build/reports/detekt.xml")
    }
    html {
      enabled = true
      destination = file("build/reports/detekt.html")
    }
    txt {
      enabled = true
      destination = file("build/reports/detekt.txt")
    }
    custom {
      reportId = "CustomJsonReport"
      destination = file("build/reports/detekt.json")
    }
  }
}

fun getProperty(propertyName: String): String =
  project.findProperty(propertyName).toString()

tasks {
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
  }
}
