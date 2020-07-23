val postgresVersion = "42.2.14"
val hikariCpVersion = "3.4.5"
val flywayVersion = "6.4.4"
val ktorServerVersion = "1.3.1"
val exposedVersion = "0.25.1"
val kotlinVersion = "0.20.0"
val jacksonDataTypeVersion = "2.10.3"
val ariesCommonsTestVersion = "0.0.1"

plugins {
  kotlin("jvm")
  id("io.gitlab.arturbosch.detekt")
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

repositories {
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.postgresql:postgresql:$postgresVersion")
  implementation("com.zaxxer:HikariCP:$hikariCpVersion")
  implementation("org.flywaydb:flyway-core:$flywayVersion")
  implementation("io.ktor:ktor-server-core:$ktorServerVersion")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

  api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonDataTypeVersion")
  api("org.jetbrains.exposed:exposed-core:$exposedVersion")
  api("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

  //  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesCommonsTestVersion")
  testImplementation(project(":commons:commons.test"))
}

detekt {
  buildUponDefaultConfig = false
  input = files("src/main/kotlin")
  config = files("src/main/resources/detekt.yml")
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

