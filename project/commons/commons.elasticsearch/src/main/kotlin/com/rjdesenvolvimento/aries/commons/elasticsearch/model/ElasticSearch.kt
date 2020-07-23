package com.rjdesenvolvimento.aries.commons.elasticsearch.model

import org.elasticsearch.client.*

class ElasticSearch(
  val host: String,
  val port: Int,
  val elkUsername: String,
  val elkPassword: String,
  val https: Boolean,
  val userSniffer: Boolean
)

fun ElasticSearch.createClient(): RestHighLevelClient {
  return create(
    host = host,
    port = port,
    https = https,
    user = elkUsername,
    password = elkPassword,
    useSniffer = userSniffer,
    sniffIntervalMillis = 30000,
    sniffAfterFailureDelayMillis = 2000
  )
}
