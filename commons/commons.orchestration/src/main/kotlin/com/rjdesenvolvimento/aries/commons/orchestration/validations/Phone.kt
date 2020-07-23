package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.utils.*
import java.util.regex.*

fun validateIDD(language: Language, idd: String): String? {
  return when (idd.trim()) {
    "" -> notNull(language, "IDD")
    else -> {
      if (Pattern.compile(iddPattern).matcher(idd).matches()) null
      else invalidField(language, "IDD")
    }
  }
}

fun validateDDD(language: Language, ddd: String): String? {
  return when (ddd.trim()) {
    "" -> notNull(language, "DDD")
    else -> {
      if (Pattern.compile(dddPattern).matcher(ddd).matches()) null
      else invalidField(language, "DDD")
    }
  }
}

fun validatePhone(language: Language, phone: String): String? {
  return when (phone.trim()) {
    "" -> notNull(language, "phone number")
    else -> {
      if (Pattern.compile(phonePattern).matcher(phone).matches()) null
      else invalidField(language, "phone number")
    }
  }
}
