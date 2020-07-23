val kotlinElkVersion = "1.0-X-Beta-6-7.8.0"
val ariesPatternsVersion = "0.0.1"
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
  maven { url = uri("https://jitpack.io") }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
//  implementation("com.rjdesenvolvimento.aries:commons.patterns:$ariesPatternsVersion")
  implementation(project(":commons:commons.patterns"))

  api("com.github.jillesvangurp:es-kotlin-wrapper-client:$kotlinElkVersion")

//  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesTestVersion")
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


