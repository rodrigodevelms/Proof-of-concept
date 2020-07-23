package com.rjdesenvolvimento.aries.access.consumer.phone

import com.rjdesenvolvimento.aries.access.consumer.person.*
import com.rjdesenvolvimento.aries.commons.consumer.entity.*
import org.jetbrains.exposed.sql.*
import java.util.*

data class Phone(
  override val id: UUID,
  override val active: Boolean,
  val idd: String,
  val ddd: String,
  val number: String,
  val personFk: UUID
) : BaseEntity()

abstract class PhoneBaseTable(schema: String) : Table("$schema.phone") {
  val id = uuid("id")
  val active = bool("active")
  val idd = varchar("idd", 20)
  val ddd = varchar("ddd", 20)
  val number = varchar("number", 20)
  val personFk = reference("person_fk", personTable(schema).id, ReferenceOption.CASCADE)
  override val primaryKey: PrimaryKey = PrimaryKey(id, name = "phone_id_uindex")
}

fun phoneTable(schema: String): PhoneBaseTable = object : PhoneBaseTable(schema) {}
