@file:Suppress("Duplicates")

package com.rjdesenvolvimento.aries.access.consumer.employee

import com.rjdesenvolvimento.aries.access.consumer.consumer.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.*

suspend fun insertEmployee(employee: Employee, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    employeeTable(schema).insert {
      it[id] = employee.id
      it[active] = employee.active
      it[personFk] = employee.personFk
    }
    contractTable(schema).insert { contractTable ->
      contractTable[id] = employee.contract.id
      contractTable[active] = employee.contract.active
      contractTable[workRegime] = employee.contract.workRegime.value
      contractTable[hiringDate] = employee.contract.hiringDate
      contractTable[resignationDate] = employee.contract.resignationDate
      contractTable[remuneration] = employee.contract.remuneration
      contractTable[role] = employee.contract.role
      contractTable[workday] = employee.contract.workday.value
      contractTable[employeeFk] = employee.contract.employeeFk
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun updateContract(contract: Contract, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    contractTable(schema).update({ employeeTable(schema).id eq contract.id }) {
      it[workRegime] = contract.workRegime.value
      it[hiringDate] = contract.hiringDate
      it[resignationDate] = contract.resignationDate
      it[remuneration] = contract.remuneration
      it[role] = contract.role
      it[workday] = contract.workday.value
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun statusEmployee(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    employeeTable(schema).update({ employeeTable(schema).id eq consumer.id }) {
      it[active] = consumer.target!!.status!!
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun deleteEmployee(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    employeeTable(schema).deleteWhere { employeeTable(schema).id eq consumer.id }
    contractTable(schema).deleteWhere { contractTable(schema).employeeFk eq consumer.id }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}
