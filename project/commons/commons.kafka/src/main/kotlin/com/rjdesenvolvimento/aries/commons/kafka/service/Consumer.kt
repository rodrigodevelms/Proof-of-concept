@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.kafka.service

import com.fasterxml.jackson.module.kotlin.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.kafka.kafka.*
import com.rjdesenvolvimento.aries.commons.kafka.utils.*
import com.rjdesenvolvimento.aries.commons.patterns.io.*
import kotlinx.coroutines.*
import org.apache.kafka.common.*
import java.time.*

class KConsumer(
  val topic: String,
  val bootstrapServers: String,
  val group: String,
  val autoCommit: Boolean = false,
  val offsetBehaviour: OffsetBehavior = OffsetBehavior.Earliest,
  val pollDuration: Long = 1000
)

suspend inline fun <reified T : Any> KConsumer.consumeEvent(
  timeDelay: Long = 100,
  noinline callback: suspend (Event<T>) -> Unit
) {
  val consume = createConsumer(
    bootstrapServers = bootstrapServers,
    group = group,
    autoCommit = autoCommit,
    offsetBehaviour = offsetBehaviour
  )
  consume.subscribe(mutableListOf(topic))
  runCatching {
    while (true) {
      retry(
        tryDo = {
          val records = consume.poll(Duration.ofMillis(pollDuration))
          if (records.count() > 0) {
            records.forEach {
              val event = treeToValue(
                it.value(),
                jacksonTypeRef<Event<T>>()
              )
              runCatching {
                callback(event)
              }.onSuccess {
                publishResponse(topic = topic, event = event, isSuccess = true, bootstrapServers = bootstrapServers)
              }.onFailure {
                publishResponse(topic = topic, event = event, isSuccess = false, bootstrapServers = bootstrapServers)
                delay(timeDelay)
              }
            }
            consume.commitAsync()
          }
        },
        onErrorDo = { throw KafkaException("Consumer error cod: 001") }
      )
    }
  }.onFailure {
    it.message
    it.printStackTrace()
    consume.commitSync()
  }
}

suspend fun <T> publishResponse(
  topic: String,
  event: Event<T>,
  isSuccess: Boolean,
  bootstrapServers: String
) {
  KProducer(
    topic = "response-$topic",
    event = responseEvent(
      event = event,
      eventType = EventType.Response,
      eventStatus = if (isSuccess) EventStatus.Success else EventStatus.Error,
      isSuccess = isSuccess
    ),
    bootstrapServers = bootstrapServers
  ).produceEvent()
}
