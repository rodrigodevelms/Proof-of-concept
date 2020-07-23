package com.rjdesenvolvimento.aries.access.consumer.employee

import com.fasterxml.jackson.databind.annotation.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.rjdesenvolvimento.aries.access.consumer.person.*
import com.rjdesenvolvimento.aries.commons.consumer.entity.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.*
import java.math.*
import java.time.*
import java.util.*

data class Employee(
  override val id: UUID,
  override val active: Boolean,
  val contract: Contract,
  val personFk: UUID
) : BaseEntity()

data class Contract(
  override val id: UUID,
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
  val employeeFk: UUID
) : BaseEntity()

abstract class EmployeeTable(schema: String) : Table("$schema.employee") {
  val id = uuid("id")
  val active = bool("active")
  val personFk = reference("person_fk", personTable(schema).id, ReferenceOption.CASCADE)
  override val primaryKey: PrimaryKey = PrimaryKey(id, name = "employee_id_uindex")
}

fun employeeTable(schema: String): EmployeeTable = object : EmployeeTable(schema) {}

abstract class ContractTable(schema: String) : Table("$schema.contract") {
  val id = uuid("id")
  val active = bool("active")
  val workRegime = varchar("work_regime", 15)
  val hiringDate = date("hiring_date")
  val resignationDate = date("resignation_date").nullable()
  val remuneration = decimal("remuneration", 10, 2)
  val role = varchar("role", 120)
  val workday = varchar("workday", 15)
  val employeeFk = reference("employee_fk", employeeTable(schema).id, ReferenceOption.CASCADE)
  override val primaryKey: PrimaryKey = PrimaryKey(id, name = "contract_id_uindex")
}

fun contractTable(schema: String): ContractTable = object : ContractTable(schema) {}
