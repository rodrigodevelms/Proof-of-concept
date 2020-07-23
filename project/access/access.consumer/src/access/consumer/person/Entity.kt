package com.rjdesenvolvimento.aries.access.consumer.person

import com.fasterxml.jackson.databind.annotation.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.rjdesenvolvimento.aries.access.consumer.phone.*
import com.rjdesenvolvimento.aries.commons.consumer.entity.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.*
import java.time.*
import java.util.*

data class Person(
  override val id: UUID,
  override val active: Boolean,
  val name: String,
  val email: String,
  val document: String,
  @JsonSerialize(using = LocalDateSerializer::class)
  @JsonDeserialize(using = LocalDateDeserializer::class)
  val birthdate: LocalDate,
  val maritalStatus: MaritalStatus,
  val gender: Gender,
  val phones: Collection<Phone>
) : BaseEntity()

abstract class PersonBaseTable(schema: String) : Table("$schema.person") {
  val id = uuid("id")
  val active = bool("active")
  val name = varchar("name", 120)
  val email = varchar("email", 120).uniqueIndex()
  val document = varchar("document", 30).uniqueIndex()
  val birthdate = date("birthdate")
  val maritalStatus = varchar("marital_status", 15)
  val gender = varchar("gender", 15)
  override val primaryKey: PrimaryKey = PrimaryKey(id, name = "person_id_uindex")
}

fun personTable(schema: String): PersonBaseTable = object : PersonBaseTable(schema) {}
