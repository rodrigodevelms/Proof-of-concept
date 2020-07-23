val kafkaVersion = "2.5.0"
val ktorVersion = "1.3.2"
val kotlinCoroutineVersion = "1.3.7"
val ariesPatternsVersion = "0.0.1"
val ariesTestVersion = "0.0.1"

plugins {
  kotlin("jvm") version "1.3.72"
  id("io.gitlab.arturbosch.detekt") version "1.10.0"
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

repositories {
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
  maven { url = uri("http://packages.confluent.io/maven/") }
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
  implementation("org.apache.kafka:connect-json:$kafkaVersion")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion")
  implementation("com.rjdesenvolvimento.aries:commons.patterns:$ariesPatternsVersion")

  testImplementation("com.rjdesenvolvimento.aries:commons.test:$ariesTestVersion")

  api("io.ktor:ktor-jackson:$ktorVersion")
  api("org.apache.kafka:kafka-clients:$kafkaVersion")
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
  withType<io.gitlab.arturbosch.detekt.Detekt> { this.jvmTarget = "11"}
  test {
    useJUnitPlatform()
    testLogging {
      events("passed", "skipped", "failed")
    }
  }
}
