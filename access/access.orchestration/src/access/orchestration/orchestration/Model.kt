package com.rjdesenvolvimento.aries.access.orchestration.orchestration

import com.rjdesenvolvimento.aries.access.orchestration.address.*
import com.rjdesenvolvimento.aries.access.orchestration.employee.*
import com.rjdesenvolvimento.aries.access.orchestration.legalperson.*
import com.rjdesenvolvimento.aries.access.orchestration.person.*
import com.rjdesenvolvimento.aries.access.orchestration.phone.*
import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*

import java.util.*

data class Orchestration(
  override val id: UUID?,
  override val active: Boolean,
  var target: TargetClass?,
  val person: Person?,
  val phone: Phone?,
  val legalPerson: LegalPerson?,
  val employee: Employee?,
  val address: Address?
) : BaseModel()

fun copyOrchestrator(
  orchestration: Orchestration,
  uuid: UUID? = null
): Orchestration {
  val id = uuid ?: UUID.randomUUID()
  return orchestration.copy(
    id = id,
    active = true,
    person = orchestration.person?.let { copyPerson(id, it) },
    phone = orchestration.phone?.let { copyPhone(it, id) },
    legalPerson = orchestration.legalPerson?.let { copyLegalPerson(it, id) },
    employee = orchestration.employee?.let { copyEmployee(it, id) },
    address = orchestration.address?.let { copyAddress(it, id) }
  )
}

data class TargetClass(
  val target: String,
  val status: Boolean?
)

fun orchestrationValidator(language: Language, orchestration: Orchestration): List<String> {
  return listOfNotNull(
    orchestration.person?.let { personValidator(language, it) },
    orchestration.phone?.let { phoneValidator(language, it) },
    orchestration.address?.let { addressValidator(language, it) },
    orchestration.legalPerson?.let { legalPersonValidator(language, it) },
    orchestration.employee?.let { employeeValidator(language, it) }).flatMap { it.toList() }
}
