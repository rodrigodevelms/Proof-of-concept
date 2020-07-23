@file:Suppress("unused")

package com.rjdesenvolvimento.aries.access.orchestration.employee

import com.fasterxml.jackson.annotation.*
import com.rjdesenvolvimento.aries.access.orchestration.utils.*
import com.rjdesenvolvimento.aries.commons.kafka.utils.*
import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.model.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import io.ktor.util.*
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
  @JsonFormat(pattern = "yyyy-MM-dd")
  val hiringDate: LocalDate,
  @JsonFormat(pattern = "yyyy-MM-dd")
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

@KtorExperimentalAPI
fun employeeValidator(language: Language, employee: Employee): List<String> {
  return listOfNotNull(
    validateDate(
      language = language,
      fieldName = "hiring date",
      date = employee.contract.hiringDate,
      differenceInDays = 1L
    ),
    validateMonetary(
      language = language,
      fieldName = "remuneration",
      value = employee.contract.remuneration,
      paramValue = BigDecimal(environmentVariable(ev, "utils.base_salary"))
    ),
    validateString(
      language = language,
      fieldName = "role",
      value = employee.contract.role,
      minimumValue = 5,
      maximumValue = 120
    ),
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
