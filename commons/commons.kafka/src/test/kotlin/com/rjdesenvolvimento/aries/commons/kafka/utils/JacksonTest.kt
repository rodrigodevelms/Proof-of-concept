package com.rjdesenvolvimento.aries.commons.kafka.utils

import com.fasterxml.jackson.module.kotlin.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*
import java.util.*
import kotlin.test.*

class JacksonTest {
  @Test
  fun valueToTreeSuccessfullyConverted() {
    val event = Event(
      UUID.randomUUID(),
      UUID.randomUUID(),
      Information(
        EventType.Action,
        EventStatus.Open,
        EventMethod.Insert
      ),
      Request(
        "token",
        "room",
        "schema"
      ),
      "Testing"
    )
    val encoder = valueToTree(event)
    val decoder = treeToValue<String>(encoder, jacksonTypeRef())
    asserter.assertEquals("Testing UUID converted", "Testing", decoder.message)
  }
}
