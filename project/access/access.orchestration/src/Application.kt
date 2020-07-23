package com.rjdesenvolvimento.aries

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.module.kotlin.*
import com.rjdesenvolvimento.aries.access.orchestration.orchestration.*
import com.rjdesenvolvimento.aries.access.orchestration.settings.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.apache.kafka.common.errors.*
import javax.security.sasl.AuthenticationException

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
  install(CORS) {
    method(HttpMethod.Options)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Patch)
    header(HttpHeaders.Authorization)
    header("MyCustomHeader")
    allowCredentials = true
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
  }

  install(Authentication) {
  }

  install(ContentNegotiation) {
    jackson {
      registerModules(KotlinModule(), JavaTimeModule())
      jacksonObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
  }

  routing {
    orchestration()

    install(StatusPages) {
      exception<AuthenticationException> { cause ->
        call.respond(HttpStatusCode.Unauthorized)
      }
      exception<AuthorizationException> { cause ->
        call.respond(HttpStatusCode.Forbidden)
      }
    }
  }
  elkClient
}

