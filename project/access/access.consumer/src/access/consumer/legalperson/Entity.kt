package com.rjdesenvolvimento.aries.access.consumer.legalperson

import com.fasterxml.jackson.annotation.*
import com.rjdesenvolvimento.aries.access.consumer.person.*
import com.rjdesenvolvimento.aries.commons.consumer.entity.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.*
import java.time.*
import java.util.*

data class LegalPerson(
  override val id: UUID,
  override val active: Boolean,
  val companyName: String,
  val fancyName: String,
  val document: String,
  @JsonFormat(pattern = "yyyy-MM-dd")
  val openingDate: LocalDate,
  val legalNature: LegalNature,
  val lineOfBusiness: String,
  val personFk: UUID
) : BaseEntity()

abstract class LegalPersonTable(schema: String) : Table("$schema.legal_person") {
  val id = uuid("id")
  val active = bool("active")
  val companyName = varchar("company_name", 120)
  val fancyName = varchar("fancy_name", 120)
  val document = varchar("document", 30)
  val openingDate = date("opening_date")
  val legalNature = varchar("legal_nature", 15)
  val lineOfBusiness = varchar("line_of_business", 120)
  val personFk = reference("person_fk", personTable(schema).id, ReferenceOption.CASCADE)
  override val primaryKey: PrimaryKey = PrimaryKey(id, name = "legal_person_id_uindex")
}

fun legalPersonTable(schema: String): LegalPersonTable = object : LegalPersonTable(schema) {}
