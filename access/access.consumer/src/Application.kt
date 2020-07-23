package com.rjdesenvolvimento.aries

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.module.kotlin.*
import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import com.rjdesenvolvimento.aries.access.consumer.settings.*
import com.rjdesenvolvimento.aries.commons.consumer.settings.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.util.*
import kotlinx.coroutines.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

  Network.startNetWork()

  install(ContentNegotiation) {
    jackson {
      enable(SerializationFeature.INDENT_OUTPUT)
      registerModules(KotlinModule(), JavaTimeModule())
      jacksonObjectMapper()
    }
  }

  launch { serviceConsumer() }
  elkClient
}

