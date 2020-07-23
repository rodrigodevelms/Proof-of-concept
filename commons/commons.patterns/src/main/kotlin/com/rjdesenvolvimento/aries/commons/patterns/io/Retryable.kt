@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.patterns.io

import kotlinx.coroutines.*

suspend fun <T> retry(
  tryDo: suspend () -> T?,
  onErrorDo: suspend () -> T?,
  times: Int = Int.MAX_VALUE,
  initialDelay: Long = 100,
  maxDelay: Long = 1000,
  factor: Double = 2.0
): T? {
  var currentDelay = initialDelay
  repeat(times - 1) {
    runCatching {
      return tryDo()
    }.onFailure {
      when (it) {
        is RuntimeException -> {
          it.printStackTrace()
          throw it
        }
        is ClassCastException -> {
          it.printStackTrace()
          throw it
        }
        else -> {
          it.printStackTrace()
        }
      }
    }
    delay(currentDelay)
    currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
  }
  return onErrorDo()
}
