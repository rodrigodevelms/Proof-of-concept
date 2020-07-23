@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.orchestration.model.employee

enum class WorkRegime(val value: String) {
  Contract("Contract"),
  WorkLaws("Work Laws")
}

enum class Workday(val value: String) {
  Daytime("Daytime"),
  Nightly("Nightly")
}
