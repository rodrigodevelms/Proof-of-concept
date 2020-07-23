@file:Suppress("Duplicates")

package com.rjdesenvolvimento.aries.access.consumer.address

import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.*

suspend fun insertAddress(address: Address, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    addressTable(schema).insert {
      it[id] = address.id
      it[active] = address.active
      it[country] = address.country
      it[state] = address.state
      it[city] = address.city
      it[zipCode] = address.zipCode
      it[district] = address.district
      it[publicPlace] = address.publicPlace
      it[number] = address.number
      it[complement] = address.complement
      it[personFk] = address.personFk
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun updateAddress(address: Address, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    addressTable(schema).update({ addressTable(schema).id eq address.id }) {
      it[country] = address.country
      it[state] = address.state
      it[city] = address.city
      it[zipCode] = address.zipCode
      it[district] = address.district
      it[publicPlace] = address.publicPlace
      it[number] = address.number
      it[complement] = address.complement
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun deleteAddress(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    addressTable(schema).deleteWhere { addressTable(schema).id eq consumer.id }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}
