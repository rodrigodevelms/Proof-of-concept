plugins {
  kotlin("jvm")
  `maven-publish`
}

group = "com.rjdesenvolvimento.aries"
version = "0.0.1"

repositories {
  jcenter()
  maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
  implementation(kotlin("stdlib-jdk8", "_"))

  api("ch.qos.logback:logback-classic:_")
  api("org.junit.jupiter:junit-jupiter-engine:_")
  api("org.jetbrains.kotlin:kotlin-test:_")
  api("org.jetbrains.kotlin:kotlin-test-junit5:_")
  api("io.mockk:mockk:_")
}


