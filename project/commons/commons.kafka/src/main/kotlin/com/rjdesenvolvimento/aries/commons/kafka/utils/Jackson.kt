package com.rjdesenvolvimento.aries.commons.kafka.utils

import com.fasterxml.jackson.core.type.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*

fun valueToTree(obj: Any): JsonNode {
  return jacksonObjectMapper().valueToTree(obj)
}

fun <T> treeToValue(value: JsonNode, event: TypeReference<Event<T>>): Event<T> {
  return jacksonObjectMapper().readValue(value.toString(), event)
}
