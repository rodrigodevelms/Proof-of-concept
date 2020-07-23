package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import java.time.*
import java.time.temporal.*

fun validateDate(language: Language, fieldName: String, date: LocalDate, differenceInDays: Long ): String? {
  val comparableDate = ChronoUnit.DAYS.between(date, LocalDate.now())
  return when {
    comparableDate >= differenceInDays -> null
    else -> invalidDatePeriod(language, fieldName, differenceInDays)
  }
}
