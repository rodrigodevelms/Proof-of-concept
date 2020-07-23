package com.rjdesenvolvimento.aries.access.consumer.consumer

import com.rjdesenvolvimento.aries.access.consumer.settings.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*
import com.rjdesenvolvimento.aries.commons.kafka.service.*

import io.ktor.util.*

const val TOPIC_NAME = "access-domain"
const val BOOTSTRAP_SERVER = "http://localhost:9092"
const val GROUP = "access-domain-group"

@KtorExperimentalAPI
suspend inline fun serviceConsumer() {
  runCatching {
    elasticStatus()
    Consumer(
      TOPIC_NAME,
      BOOTSTRAP_SERVER,
      GROUP
    ).consumeEvent { event: Event<Consumer> ->
      when (event.information.eventMethod) {
        EventMethod.Insert -> {
          insertConsumer(consumer = event.message, schema = event.request.schema)
        }
        EventMethod.Update -> {
          updateConsumer(consumer = event.message, schema = event.request.schema)
        }
        EventMethod.Status -> {
          statusConsumer(consumer = event.message, schema = event.request.schema)
        }
        EventMethod.Delete -> {
          deleteConsumer(consumer = event.message, schema = event.request.schema)
        }
      }
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}
