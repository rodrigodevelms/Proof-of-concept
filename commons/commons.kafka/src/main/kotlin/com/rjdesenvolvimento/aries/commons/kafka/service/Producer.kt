package com.rjdesenvolvimento.aries.commons.kafka.service

import com.fasterxml.jackson.databind.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*
import com.rjdesenvolvimento.aries.commons.kafka.utils.*
import com.rjdesenvolvimento.aries.commons.patterns.io.*
import org.apache.kafka.clients.producer.*
import org.apache.kafka.common.*

class KProducer<T>(
  val topic: String,
  val event: Event<T>,
  val bootstrapServers: String,
  val retries: Int = Int.MAX_VALUE,
  val linger: Int = 10,
  val batchSize: BatchSize = BatchSize.ThirtyTwo
)

suspend inline fun <T> KProducer<T>.produceEvent() {
  val producer = createProducer(
    bootstrapServers = bootstrapServers,
    retries = retries,
    linger = linger,
    batchSize = batchSize
  )
  retry(
    tryDo = {
      val record = ProducerRecord<JsonNode, JsonNode>(
        topic,
        valueToTree(event.id),
        valueToTree(event)
      )
      producer.dispatch(record)
    },
    onErrorDo = {
      throw KafkaException("Producer error cod: 0001")
    }
  )

}
