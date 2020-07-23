@file:Suppress("Duplicates")

package com.rjdesenvolvimento.aries.access.consumer.person

import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import com.rjdesenvolvimento.aries.access.consumer.phone.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.*

suspend fun insertPerson(person: Person, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    personTable(schema).insert {
      it[id] = person.id
      it[active] = person.active
      it[name] = person.name
      it[email] = person.email
      it[document] = person.document
      it[birthdate] = person.birthdate
      it[maritalStatus] = person.maritalStatus.name
      it[gender] = person.gender.name
    }
    phoneTable(schema).batchInsert(person.phones) { phone ->
      this[phoneTable(schema).id] = phone.id
      this[phoneTable(schema).active] = phone.active
      this[phoneTable(schema).idd] = phone.idd
      this[phoneTable(schema).ddd] = phone.ddd
      this[phoneTable(schema).number] = phone.number
      this[phoneTable(schema).personFk] = phone.personFk
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun updatePerson(person: Person, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    personTable(schema).update({ personTable(schema).id eq person.id }) {
      it[name] = person.name
      it[email] = person.email
      it[document] = person.document
      it[birthdate] = person.birthdate
      it[maritalStatus] = person.maritalStatus.name
      it[gender] = person.gender.name
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun statusPerson(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    personTable(schema).update({ personTable(schema).id eq consumer.id }) {
      it[active] = consumer.target!!.status!!
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun deletePerson(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    personTable(schema).deleteWhere { personTable(schema).id eq consumer.id }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}
