@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.orchestration.model.legalperson

import com.fasterxml.jackson.databind.annotation.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import java.time.*
import java.util.*

data class LegalPerson(
  override val id: UUID?,
  override val active: Boolean,
  val companyName: String,
  val fancyName: String,
  val document: String,
  @JsonSerialize(using = LocalDateSerializer::class)
  @JsonDeserialize(using = LocalDateDeserializer::class)
  val openingDate: LocalDate,
  val legalNature: LegalNature,
  val lineOfBusiness: String,
  val personFk: UUID?
) : BaseModel()

fun copyLegalPerson(legalPerson: LegalPerson, personFk: UUID): LegalPerson {
  return legalPerson.copy(
    id = UUID.randomUUID(),
    personFk = personFk
  )
}

fun legalPersonValidator(language: Language, legalPerson: LegalPerson): List<String> {
  return listOfNotNull(
    validateString(language = language, fieldName = "company name", value = legalPerson.companyName, minimumValue = 5, maximumValue = 120),
    validateString(language = language, fieldName = "fancy name", value = legalPerson.fancyName, minimumValue = 5, maximumValue = 120),
    validateDocument(language = language, fieldName = "document", document = legalPerson.document),
    validateDate(language = language, fieldName = "opening date", date = legalPerson.openingDate, differenceInDays = 1L),
    validateString(language = language, fieldName = "line of business", value = legalPerson.lineOfBusiness, minimumValue = 5, maximumValue = 120)
  )
}
