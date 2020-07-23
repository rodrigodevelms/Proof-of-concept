package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*

fun validateString(language: Language, fieldName: String, value: String, minimumValue: Int, maximumValue: Int): String? {
  return when {
    value.isNullOrBlank() -> notNull(language, fieldName)
    value.length < minimumValue || value.length > maximumValue -> invalidStringLength(language, fieldName, minimumValue, maximumValue)
    else -> null
  }
}
