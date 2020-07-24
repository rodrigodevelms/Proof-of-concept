plugins {
  application
  kotlin("jvm")
  id("com.github.johnrengelman.shadow")
  id("org.flywaydb.flyway")
  id("io.gitlab.arturbosch.detekt")
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
  implementation(kotlin("stdlib-jdk8", "_"))
  implementation("ch.qos.logback:logback-classic:_")
  implementation("io.ktor:ktor-server-netty:_")
  implementation("io.ktor:ktor-server-core:_")
  implementation("io.ktor:ktor-jackson:_")
  implementation(project(":commons:commons.consumer"))
  implementation(project(":commons:commons.kafka"))
  implementation(project(":commons:commons.elasticsearch"))

  testImplementation("io.ktor:ktor-server-tests:_")
  testImplementation(project(":commons:commons.test"))
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

flyway {
  url = System.getenv("URL")
  user = System.getenv("PSQL_USER")
  password = System.getenv("PSQL_PASSWORD")
  baselineOnMigrate = true
  locations = arrayOf("filesystem:resources/db/migration")
}
