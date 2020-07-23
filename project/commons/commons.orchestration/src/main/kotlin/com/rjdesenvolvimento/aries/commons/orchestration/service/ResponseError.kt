@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.orchestration.service

import com.rjdesenvolvimento.aries.commons.orchestration.`interface`.*
import java.time.*
import java.time.format.*
import java.util.*

class ResponseError(
  val id: UUID,
  val date: String,
  val errors: List<String>
) : IMessage

fun errorMsg(
  errorMessages: List<String?>
): ResponseError {
  val errorList = mutableListOf<String>()
  errorMessages.forEach { it?.let { errorList.add(it) } }
  return ResponseError(
    id = UUID.randomUUID(),
    date = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    errors = errorList
  )
}
