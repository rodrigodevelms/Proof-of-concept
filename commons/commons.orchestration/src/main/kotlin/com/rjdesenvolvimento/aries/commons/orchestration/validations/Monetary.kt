package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import java.math.*

fun validateMonetary(language: Language, fieldName: String, value: BigDecimal, paramValue: BigDecimal): String? {
  return if (value > paramValue) null
  else invalidField(language, fieldName)
}
