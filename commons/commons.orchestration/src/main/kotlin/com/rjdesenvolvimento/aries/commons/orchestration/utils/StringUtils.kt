package com.rjdesenvolvimento.aries.commons.orchestration.utils

fun onlyNumbers(string: String): String {
  return string.replace(Regex("[^0-9]"), "")
}
