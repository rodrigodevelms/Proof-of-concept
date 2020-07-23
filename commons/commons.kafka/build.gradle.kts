plugins {
  kotlin("jvm")
  id("io.gitlab.arturbosch.detekt")
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

repositories {
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
  maven { url = uri("http://packages.confluent.io/maven/") }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  implementation("org.apache.kafka:connect-json:_")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
  implementation(project(":commons:commons.patterns"))

  testImplementation(project(":commons:commons.test"))

  api("io.ktor:ktor-jackson:_")
  api("org.apache.kafka:kafka-clients:_")
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
