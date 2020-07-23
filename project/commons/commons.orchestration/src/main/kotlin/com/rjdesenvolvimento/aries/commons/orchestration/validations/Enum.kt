package com.rjdesenvolvimento.aries.commons.orchestration.validations

inline fun <reified T : Enum<T>> validateEnum(value: String): String? {
  return if (enumValues<T>().any() { it.name == value }) null
  else "O enum é inválido"
}
