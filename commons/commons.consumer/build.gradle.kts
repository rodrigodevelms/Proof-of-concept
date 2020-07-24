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
  implementation(kotlin("stdlib-jdk8", "_"))
  implementation("org.postgresql:postgresql:_")
  implementation("com.zaxxer:HikariCP:_")
  implementation("org.flywaydb:flyway-core:_")
  implementation("io.ktor:ktor-server-core:_")
  implementation("org.jetbrains.exposed:exposed-jdbc:_")

  api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:_")
  api("org.jetbrains.exposed:exposed-core:_")
  api("org.jetbrains.exposed:exposed-java-time:_")

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

