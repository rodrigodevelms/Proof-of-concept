@file:Suppress("unused")

package com.rjdesenvolvimento.aries.access.orchestration.employee

enum class WorkRegime(val value: String) {
  Contract("Contract"),
  WorkLaws("Work Laws")
}

enum class Workday(val value: String) {
  Daytime("Daytime"),
  Nightly("Nightly")
}
