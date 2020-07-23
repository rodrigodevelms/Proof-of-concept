package com.rjdesenvolvimento.aries.commons.kafka.kafka

import com.fasterxml.jackson.databind.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import org.apache.kafka.clients.producer.*
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.connect.json.JsonSerializer
import kotlin.coroutines.*

fun createProducer(
  bootstrapServers: String,
  retries: Int,
  linger: Int,
  batchSize: BatchSize
): KafkaProducer<JsonNode, JsonNode> {
  val prop: HashMap<String, Any> = HashMap()
  prop[BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
  prop[KEY_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java.name
  prop[VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java.name
  prop[ENABLE_IDEMPOTENCE_CONFIG] = "true"
  prop[ACKS_CONFIG] = "all"
  prop[RETRIES_CONFIG] = retries.toString()
  prop[COMPRESSION_TYPE_CONFIG] = "snappy"
  prop[LINGER_MS_CONFIG] = linger.toString()
  prop[BATCH_SIZE_CONFIG] = batchSize.value
  return KafkaProducer(prop)
}

suspend inline fun <reified K : Any, reified V : Any> KafkaProducer<K, V>.dispatch(record: ProducerRecord<K, V>) =
  suspendCoroutine<RecordMetadata> { continuation ->
    val callback = Callback { metadata, exception ->
      if (metadata == null) {
        continuation.resumeWithException(exception!!)
      } else {
        continuation.resume(metadata)
      }
    }
    this.send(record, callback)
  }
