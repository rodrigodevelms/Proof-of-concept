package com.rjdesenvolvimento.aries.commons.kafka.kafka

import com.fasterxml.jackson.databind.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import org.apache.kafka.clients.consumer.*
import org.apache.kafka.clients.consumer.ConsumerConfig.*
import org.apache.kafka.connect.json.JsonDeserializer

fun createConsumer(
  bootstrapServers: String,
  group: String,
  autoCommit: Boolean,
  offsetBehaviour: OffsetBehavior
): KafkaConsumer<JsonNode, JsonNode> {
  val prop: HashMap<String, Any> = HashMap()
  prop[BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
  prop[KEY_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name
  prop[VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java.name
  prop[GROUP_ID_CONFIG] = group
  prop[AUTO_OFFSET_RESET_CONFIG] = offsetBehaviour.value
  prop[ENABLE_AUTO_COMMIT_CONFIG] = autoCommit
  return KafkaConsumer(prop)
}
