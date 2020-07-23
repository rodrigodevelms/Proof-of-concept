@file:Suppress("Duplicates")

package com.rjdesenvolvimento.aries.access.consumer.legalperson

import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.*

suspend fun insertLegalPerson(legalPerson: LegalPerson, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    legalPersonTable(schema).insert {
      it[id] = legalPerson.id
      it[active] = legalPerson.active
      it[companyName] = legalPerson.companyName
      it[fancyName] = legalPerson.fancyName
      it[document] = legalPerson.document
      it[openingDate] = legalPerson.openingDate
      it[legalNature] = legalPerson.legalNature.value
      it[lineOfBusiness] = legalPerson.lineOfBusiness
      it[personFk] = legalPerson.personFk
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun updateLegalPerson(legalPerson: LegalPerson, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    legalPersonTable(schema).update({ legalPersonTable(schema).id eq legalPerson.id }) {
      it[companyName] = legalPerson.companyName
      it[fancyName] = legalPerson.fancyName
      it[document] = legalPerson.document
      it[openingDate] = legalPerson.openingDate
      it[legalNature] = legalPerson.legalNature.value
      it[lineOfBusiness] = legalPerson.lineOfBusiness
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun statusLegalPerson(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    legalPersonTable(schema).update({ legalPersonTable(schema).id eq consumer.id }) {
      it[active] = consumer.target!!.status!!
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun deleteLegalPerson(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    legalPersonTable(schema).deleteWhere { legalPersonTable(schema).id eq consumer.id }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}
