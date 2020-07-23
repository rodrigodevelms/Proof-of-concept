package com.rjdesenvolvimento.aries.access.orchestration.utils

import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*
import com.rjdesenvolvimento.aries.commons.kafka.service.*
import com.rjdesenvolvimento.aries.commons.kafka.utils.*
import io.ktor.util.*
import java.util.*

@KtorExperimentalAPI
class OrchestrationEvent<T>(
  val id: UUID,
  val taskId: UUID,
  val eventMethod: EventMethod,
  val room: String,
  val schema: String,
  val message: T
)

@KtorExperimentalAPI
suspend fun <T> OrchestrationEvent<T>.send() {
  Producer(
    topic = environmentVariable(ev, "kafka.topic_name"),
    event = Event(
      id = id,
      taskId = taskId,
      information = Information(
        eventType = EventType.Action,
        eventStatus = EventStatus.Open,
        eventMethod = eventMethod
      ),
      request = Request(
        token = "TODO",
        room = room,
        schema = schema
      ),
      message = message
    ),
    bootstrapServers = environmentVariable(ev, "kafka.bootstrap_server")
  ).produceEvent()
}
