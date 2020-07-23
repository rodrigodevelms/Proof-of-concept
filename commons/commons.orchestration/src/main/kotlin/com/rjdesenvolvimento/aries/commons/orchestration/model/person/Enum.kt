@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.orchestration.model.person

enum class MaritalStatus(val value: String) {
  Married("Married"),
  Single("Single"),
  Divorced("Divorced"),
  StableUnion("Stable Union"),
  Widower("Widower"),
  Uninformed("Uninformed")
}

enum class Gender(val value: String) {
  Male("Male"),
  Female("Female"),
  Uninformed("Uninformed")
}
