package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.utils.*

fun validateZipCode(language: Language, zipCode: String, country: String): String? {
  return when (country.trim()) {
    "Brasil" -> {
      if (onlyNumbers(zipCode).length == 8) null
      else invalidField(language, "zip code")
    }
    "USA" -> {
      if (onlyNumbers(zipCode).length == 5) null
      else invalidField(language, "zip code")
    }
    else -> invalidField(language, "zip code")
  }
}
