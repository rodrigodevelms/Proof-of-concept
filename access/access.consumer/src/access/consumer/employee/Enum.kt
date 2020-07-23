package com.rjdesenvolvimento.aries.access.consumer.employee

enum class WorkRegime(val value: String) {
  Contract("Contract"),
  WorkLaws("Work Laws")
}

enum class Workday(val value: String) {
  Daytime("Daytime"),
  Nightly("Nightly")
}
