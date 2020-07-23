package com.rjdesenvolvimento.aries.access.consumer.settings

import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import com.rjdesenvolvimento.aries.access.consumer.utils.*
import com.rjdesenvolvimento.aries.commons.elasticsearch.model.*
import com.rjdesenvolvimento.aries.commons.elasticsearch.utils.*
import com.rjdesenvolvimento.aries.commons.kafka.utils.*
import io.inbot.eskotlinwrapper.*
import io.ktor.util.*
import org.elasticsearch.client.*

@KtorExperimentalAPI
val elkClient: RestHighLevelClient = ElasticSearch(
  environmentVariable(ev, "elk.host"),
  environmentVariable(ev, "elk.port").toInt(),
  environmentVariable(ev, "elk.username"),
  environmentVariable(ev, "elk.password"),
  environmentVariable(ev, "elk.https").toBoolean(),
  environmentVariable(ev, "elk.sniffer").toBoolean()
).createClient()

@KtorExperimentalAPI
fun createOrchestrationRepository(schema: String): IndexRepository<Consumer> {
  return elkClient.indexRepository("access_domain_$schema")
}

@KtorExperimentalAPI
fun elasticStatus() = checkElasticStatus(
  environmentVariable(ev, "elk.host"),
  environmentVariable(ev, "elk.port"),
  environmentVariable(ev, "elk.authorization"),
  environmentVariable(ev, "elk.https").toBoolean()
)
