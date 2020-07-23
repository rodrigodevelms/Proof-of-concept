val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val airesCommonsOrchestrationVersion: String by project
val ariesCommonsKafkaVersion: String by project
val ariesCommonsTestVersion: String by project
val ariesCommonsElkVersion: String by project

plugins {
  application
  kotlin("jvm") version "1.3.72"
  kotlin("plugin.serialization") version "1.3.72"
  id("io.gitlab.arturbosch.detekt") version "1.10.0"
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
  implementation("com.rjdesenvolvimento.aries:commons.orchestration:$airesCommonsOrchestrationVersion")
  implementation("com.rjdesenvolvimento.aries:commons.kafka:$ariesCommonsKafkaVersion")
  implementation("com.rjdesenvolvimento.aries:commons.elasticsearch:$ariesCommonsElkVersion")

  testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesCommonsTestVersion")
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

fun getProperty(propertyName: String): String =
  project.findProperty(propertyName).toString()

tasks {
  compileKotlin { kotlinOptions.jvmTarget = "11" }
  compileTestKotlin { kotlinOptions.jvmTarget = "11" }
  withType<io.gitlab.arturbosch.detekt.Detekt> { this.jvmTarget = "11"}
  withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
  }
}
