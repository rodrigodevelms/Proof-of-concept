val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val ariesCommonsKafkaVersion: String by project
val ariesCommonsConsumerVersion: String by project
val ariesCommonsTestVersion: String by project
val ariesCommonsElkVersion: String by project

plugins {
  application
  kotlin("jvm") version "1.3.70"
  id("org.flywaydb.flyway") version "6.4.4"
  id("io.gitlab.arturbosch.detekt") version "1.10.0"
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

application {
  mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
  maven { url = uri("https://jitpack.io") }
  maven {
    url = uri(getProperty("artifactory_url"))
    credentials {
      username = getProperty("artifactory_user")
      password = getProperty("artifactory_password")
    }
  }
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
  implementation("ch.qos.logback:logback-classic:$logbackVersion")
  implementation("io.ktor:ktor-server-netty:$ktorVersion")
  implementation("io.ktor:ktor-server-core:$ktorVersion")
  implementation("io.ktor:ktor-jackson:$ktorVersion")
  implementation("com.rjdesenvolvimento.aries:commons.consumer:$ariesCommonsConsumerVersion")
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

flyway {
  url = System.getenv("URL")
  user = System.getenv("PSQL_USER")
  password = System.getenv("PSQL_PASSWORD")
  baselineOnMigrate = true
  locations = arrayOf("filesystem:resources/db/migration")
}

tasks {
  compileKotlin { kotlinOptions.jvmTarget = "11" }
  compileTestKotlin { kotlinOptions.jvmTarget = "11" }
  withType<io.gitlab.arturbosch.detekt.Detekt> { this.jvmTarget = "11"}
}
