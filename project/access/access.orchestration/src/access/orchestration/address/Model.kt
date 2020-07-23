@file:Suppress("unused")

package com.rjdesenvolvimento.aries.access.orchestration.address

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import java.util.*

data class Address(
  override val id: UUID?,
  override val active: Boolean,
  val country: String,
  val state: String,
  val city: String,
  val zipCode: String,
  val district: String,
  val publicPlace: String,
  val number: String,
  val complement: String,
  val personFk: UUID?
) : BaseModel()

fun copyAddress(address: Address, serviceFk: UUID): Address {
  return address.copy(
    id = UUID.randomUUID(),
    personFk = serviceFk
  )
}

fun addressValidator(language: Language, address: Address): List<String> {
  return listOfNotNull(
    validateString(language = language, fieldName = "country", value = address.country, minimumValue = 5, maximumValue = 120),
    validateString(language = language, fieldName = "state", value = address.state, minimumValue = 5, maximumValue = 120),
    validateString(language = language, fieldName = "city", value = address.city, minimumValue = 5, maximumValue = 120),
    validateZipCode(language = language, zipCode = address.zipCode, country = address.country),
    validateString(language = language, fieldName = "district", value = address.district, minimumValue = 5, maximumValue = 120),
    validateString(language = language, fieldName = "public place", value = address.publicPlace, minimumValue = 5, maximumValue = 120),
    validateString(language = language, fieldName = "number", value = address.number, minimumValue = 1, maximumValue = 10),
    validateString(language = language, fieldName = "complement", value = address.complement, minimumValue = 5, maximumValue = 120)
  )
}
