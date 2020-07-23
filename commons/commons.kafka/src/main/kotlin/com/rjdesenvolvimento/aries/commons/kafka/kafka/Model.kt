@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.kafka.kafka

import com.rjdesenvolvimento.aries.commons.kafka.`interface`.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import java.util.*

data class Event<T>(
  val id: UUID,
  val taskId: UUID,
  val information: Information,
  val request: Request,
  val message: T
) : IEvent

data class Information(
  val eventType: EventType,
  val eventStatus: EventStatus,
  val eventMethod: EventMethod
)

data class Request(
  val token: String,
  val room: String,
  val schema: String
)

fun <T> Event<T>.responseEvent(
  eventType: EventType,
  eventStatus: EventStatus
): Event<T> {
  return copy(
    information = information.copy(
      eventType = eventType,
      eventStatus = eventStatus
    )
  )
}
