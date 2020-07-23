val jupiterVersion = "5.6.2"
val logbackVersion = "1.2.3"
val mockkVersion = "1.10.0"

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
  implementation(kotlin("stdlib-jdk8"))

  api("ch.qos.logback:logback-classic:$logbackVersion")
  api("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
  api("org.jetbrains.kotlin:kotlin-test")
  api("org.jetbrains.kotlin:kotlin-test-junit5")
  api("io.mockk:mockk:$mockkVersion")
}


