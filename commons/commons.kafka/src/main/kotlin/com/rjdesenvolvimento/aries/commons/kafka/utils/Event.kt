@file:Suppress("unchecked_cast")

package com.rjdesenvolvimento.aries.commons.kafka.utils

import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*

fun <T> responseEvent(
  event: Event<T>,
  eventType: EventType,
  eventStatus: EventStatus,
  isSuccess: Boolean
): Event<T> {
  return event.copy(
    information = event.information.copy(
      eventType = eventType,
      eventStatus = eventStatus
    ),
    message = if (isSuccess)
      ("The Event: ${event.id}, has been successfully processed." as T)
    else
      ("The Event${event.id}: , failed to be processed." as T)
  )
}

