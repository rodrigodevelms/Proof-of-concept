@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.patterns.flow

import java.net.*
import java.net.http.*

fun checkStatus(
  target: Target,
  url: String,
  authorization: String
) {
  runCatching {
    when (target) {
      Target.Elasticsearch -> {
        response(returnConnectionStatusCode(url, authorization), target.name)
      }
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

private fun returnConnectionStatusCode(
  url: String,
  authorization: String
): Int {
  val client = HttpClient.newBuilder().build()
  val request = HttpRequest
    .newBuilder()
    .uri(URI.create(url))
    .header("Authorization", authorization)
    .build()
  return client.send(request, HttpResponse.BodyHandlers.ofString()).statusCode()
}

private fun response(code: Int, serverName: String) {
  when (code) {
    200 -> {
    }
    400 -> throw Exception("Bad Request to $serverName. No response for the server. It is up?")
    401 -> throw Exception("Unauthorized access to $serverName. Check your username and / or password.")
    else -> throw Exception("An unknown error occurred while trying to perform a request for $serverName.")
  }
}
