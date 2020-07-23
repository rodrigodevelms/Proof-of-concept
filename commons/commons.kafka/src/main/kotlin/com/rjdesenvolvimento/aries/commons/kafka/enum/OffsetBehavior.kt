@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.kafka.enum

enum class OffsetBehavior(val value: String) {
  Latest("latest"),
  Earliest("earliest"),
  None("none")
}
