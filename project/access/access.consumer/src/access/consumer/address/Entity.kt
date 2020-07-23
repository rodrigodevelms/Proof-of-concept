package com.rjdesenvolvimento.aries.access.consumer.address

import com.rjdesenvolvimento.aries.access.consumer.person.*
import com.rjdesenvolvimento.aries.commons.consumer.entity.*
import org.jetbrains.exposed.sql.*
import java.util.*

data class Address(
  override val id: UUID,
  override val active: Boolean,
  val country: String,
  val state: String,
  val city: String,
  val zipCode: String,
  val district: String,
  val publicPlace: String,
  val number: String,
  val complement: String,
  val personFk: UUID
) : BaseEntity()

abstract class AddressTable(schema: String) : Table("$schema.address") {
  val id = uuid("id")
  val active = bool("active")
  val country = varchar("country", 120)
  val state = varchar("state", 120)
  val city = varchar("city", 120)
  val zipCode = varchar("zip_code", 15)
  val district = varchar("district", 120)
  val publicPlace = varchar("public_place", 120)
  val number = varchar("number", 20)
  val complement = varchar("complement", 120)
  val personFk = reference("person_fk", personTable(schema).id, ReferenceOption.CASCADE)
  override val primaryKey: PrimaryKey = PrimaryKey(id, name = "address_id_uindex")
}

fun addressTable(schema: String): AddressTable = object : AddressTable(schema) {}
