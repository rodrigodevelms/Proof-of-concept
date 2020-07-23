val postgresVersion = "42.2.14"
val hikariCpVersion = "3.4.5"
val flywayVersion = "6.4.4"
val ktorServerVersion = "1.3.1"
val exposedVersion = "0.25.1"
val kotlinVersion = "0.20.0"
val jacksonDataTypeVersion = "2.10.3"
val ariesCommonsTestVersion = "0.0.1"

plugins {
  kotlin("jvm") version "1.3.72"
  id("io.gitlab.arturbosch.detekt") version "1.10.0"
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

repositories {
  jcenter()
  maven {
    url = uri(getProperty("artifactory_url"))
    credentials {
      username = getProperty("artifactory_user")
      password = getProperty("artifactory_password")
    }
  }
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

  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesCommonsTestVersion")

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
  repositories {
    maven {
      val releasesRepoUrl = uri(getProperty("artifactory_url"))
      val snapshotsRepoUrl = uri("$releasesRepoUrl/snapshots")
      url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
      credentials {
        username = getProperty("artifactory_user")
        password = getProperty("artifactory_password")
      }
    }
  }
}

fun getProperty(propertyName: String): String =
  project.findProperty(propertyName).toString()

tasks {
  compileKotlin { kotlinOptions.jvmTarget = "11" }
  compileTestKotlin { kotlinOptions.jvmTarget = "11" }
}
