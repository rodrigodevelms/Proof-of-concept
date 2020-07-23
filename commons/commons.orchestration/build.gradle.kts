val dataTypeVersion = "2.11.0"
val kGraphQLVersion = "0.13.2"
val ariesTestVersion = "0.0.1"

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

//  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesTestVersion")
  testImplementation(project(":commons:commons.test"))

  api("com.apurebase:kgraphql:$kGraphQLVersion")
  api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$dataTypeVersion")
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

