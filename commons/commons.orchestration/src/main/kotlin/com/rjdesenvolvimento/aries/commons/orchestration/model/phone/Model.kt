package com.rjdesenvolvimento.aries.commons.orchestration.model.phone

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import java.util.*

data class Phone(
  override val id: UUID?,
  override val active: Boolean,
  val idd: String,
  val ddd: String,
  val number: String,
  val personFk: UUID?
) : BaseModel()

fun copyPhone(phone: Phone, personFk: UUID): Phone {
  return phone.copy(id = UUID.randomUUID(), personFk = personFk)
}

fun phoneValidator(language: Language, phone: Phone): List<String> {
  return listOfNotNull(
    validateIDD(language, phone.idd),
    validateDDD(language, phone.ddd),
    validatePhone(language, phone.number)
  )
}
