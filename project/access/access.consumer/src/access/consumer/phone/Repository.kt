package com.rjdesenvolvimento.aries.access.consumer.phone

import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.*

suspend fun insertPhone(phone: Phone, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    phoneTable(schema).insert {
      it[id] = phone.id
      it[active] = phone.active
      it[idd] = phone.idd
      it[ddd] = phone.ddd
      it[number] = phone.number
      it[personFk] = phone.personFk
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun updatePhone(phone: Phone, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    phoneTable(schema).update({ phoneTable(schema).id eq phone.id }) {
      it[idd] = phone.idd
      it[ddd] = phone.ddd
      it[number] = phone.number
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun deletePhone(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    phoneTable(schema).deleteWhere { phoneTable(schema).id eq consumer.id }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}
