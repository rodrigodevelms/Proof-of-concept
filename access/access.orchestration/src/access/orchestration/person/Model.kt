@file:Suppress("unused")

package com.rjdesenvolvimento.aries.access.orchestration.person

import com.fasterxml.jackson.annotation.*
import com.rjdesenvolvimento.aries.access.orchestration.phone.*
import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import java.time.*
import java.util.*

data class Person(
  override val id: UUID? = null,
  override val active: Boolean = true,
  val name: String,
  val email: String,
  val document: String,
  @JsonFormat(pattern = "yyyy-MM-dd")
  val birthdate: LocalDate,
  val maritalStatus: MaritalStatus,
  val gender: Gender,
  val phones: List<Phone>
) : BaseModel()

fun copyPerson(id: UUID, person: Person): Person {
  return person.copy(
    id = id,
    phones = person.phones.map {
      copyPhone(
        phone = it,
        personFk = id
      )
    }
  )
}

fun personValidator(language: Language, person: Person): List<String> {
  val result = mutableListOf(
    validateString(language = language, fieldName = "name", value = person.name, minimumValue = 2, maximumValue = 120),
    validateEmail(language = language, email = person.email),
    validateDocument(language = language, fieldName = "document", document = person.document),
    validateDate(language = language, fieldName = "birthdate", date = person.birthdate, differenceInDays = 5840L)
  )
  person.phones.forEach {
    phoneValidator(language, it).forEach { message -> (result.add(message)) }
  }
  return result.filterNotNull()
}
