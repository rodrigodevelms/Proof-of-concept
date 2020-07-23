package com.rjdesenvolvimento.aries.commons.orchestration.validations

fun isnullRequest(request: String?, isNull: String): String {
  return when (request) {
    null -> isNull
    else -> request
  }
}
