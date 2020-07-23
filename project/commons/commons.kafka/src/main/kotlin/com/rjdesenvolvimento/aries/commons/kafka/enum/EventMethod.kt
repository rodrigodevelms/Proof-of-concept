@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.kafka.enum

enum class EventMethod(val value: String) {
  Insert("Insert"),
  Update("Update"),
  Status("Status"),
  Delete("Delete")
}

