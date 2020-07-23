@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.orchestration.model.employee

import com.fasterxml.jackson.databind.annotation.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import java.math.*
import java.time.*
import java.util.*

data class Employee(
  override val id: UUID?,
  override val active: Boolean,
  val contract: Contract,
  val personFk: UUID?
) : BaseModel()

data class Contract(
  override val id: UUID?,
  override val active: Boolean,
  val workRegime: WorkRegime,
  @JsonSerialize(using = LocalDateSerializer::class)
  @JsonDeserialize(using = LocalDateDeserializer::class)
  val hiringDate: LocalDate,
  @JsonSerialize(using = LocalDateSerializer::class)
  @JsonDeserialize(using = LocalDateDeserializer::class)
  val resignationDate: LocalDate?,
  val remuneration: BigDecimal,
  val role: String,
  val workday: Workday,
  val employeeFk: UUID?
) : BaseModel()

fun copyEmployee(employee: Employee, personFk: UUID): Employee {
  val id = UUID.randomUUID()
  return employee.copy(
    id = id,
    contract = copyContract(
      contract = employee.contract,
      employeeFk = id
    ),
    personFk = personFk
  )
}

fun copyContract(contract: Contract, employeeFk: UUID): Contract {
  return contract.copy(
    id = UUID.randomUUID(),
    employeeFk = employeeFk
  )
}


fun employeeValidator(language: Language, employee: Employee): List<String> {
  return listOfNotNull(
    validateDate(language = language, fieldName = "hiring date", date = employee.contract.hiringDate, differenceInDays = 1L),
    validateMonetary(language = language, fieldName = "remuneration", value = employee.contract.remuneration, paramValue = baseSalary),
    validateString(language = language, fieldName = "role", value = employee.contract.role, minimumValue = 5, maximumValue = 120),
    employee.contract.resignationDate?.let {
      validateDate(
        language = language,
        fieldName = "resignation date",
        date = it,
        differenceInDays = 0L
      )
    }
  )
}
