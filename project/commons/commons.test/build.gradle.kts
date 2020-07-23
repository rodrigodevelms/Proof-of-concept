val jupiterVersion = "5.6.2"
val logbackVersion = "1.2.3"
val mockkVersion = "1.10.0"

plugins {
  kotlin("jvm") version "1.3.72"
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

repositories {
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))

  api("ch.qos.logback:logback-classic:$logbackVersion")
  api("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
  api("org.jetbrains.kotlin:kotlin-test")
  api("org.jetbrains.kotlin:kotlin-test-junit5")
  api("io.mockk:mockk:$mockkVersion")
}

tasks {
  compileKotlin { kotlinOptions.jvmTarget = "11" }
  compileTestKotlin { kotlinOptions.jvmTarget = "11" }
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

