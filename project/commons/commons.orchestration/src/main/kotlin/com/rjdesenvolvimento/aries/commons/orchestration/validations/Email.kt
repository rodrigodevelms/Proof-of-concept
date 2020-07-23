package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.utils.*
import java.util.regex.*

fun validateEmail(language: Language, email: String): String? {
  return when (email.trim()) {
    "" -> notNull(language, "email")
    Regex(emailPattern).pattern -> null
    else -> {
      if (Pattern.compile(emailPattern).matcher(email).matches()) null
      else invalidField(language, "email")
    }
  }
}

