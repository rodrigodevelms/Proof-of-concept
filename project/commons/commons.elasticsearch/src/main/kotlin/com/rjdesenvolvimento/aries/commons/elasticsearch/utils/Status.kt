@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.elasticsearch.utils

import com.rjdesenvolvimento.aries.commons.patterns.flow.*
import com.rjdesenvolvimento.aries.commons.patterns.flow.Target

fun checkElasticStatus(
 host: String,
 port: String,
 authorization: String,
 https: Boolean
) = checkStatus(
  target = Target.Elasticsearch,
  url = if(https) "https://$host:$port" else "http://$host:$port",
  authorization = authorization
)
