package com.rjdesenvolvimento.aries.access.consumer.consumer

import com.rjdesenvolvimento.aries.access.consumer.address.*
import com.rjdesenvolvimento.aries.access.consumer.employee.*
import com.rjdesenvolvimento.aries.access.consumer.legalperson.*
import com.rjdesenvolvimento.aries.access.consumer.person.*
import com.rjdesenvolvimento.aries.access.consumer.phone.*
import com.rjdesenvolvimento.aries.access.consumer.settings.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import io.ktor.util.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.exceptions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

@KtorExperimentalAPI
suspend fun insertConsumer(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    transaction(consumer = consumer, schema = schema, eventMethod = EventMethod.Insert)
  }.onSuccess {
    runCatching {
      createOrchestrationRepository(schema).index(consumer.id.toString(), consumer)
    }.onFailure {
      transaction(consumer = consumer, schema = schema, eventMethod = EventMethod.Insert, rollback = true)
      throw it
    }
  }.onFailure {
    transaction(consumer = consumer, schema = schema, eventMethod = EventMethod.Insert, rollback = true)
    it.printStackTrace()
    throw it
  }
}

suspend fun updateConsumer(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    transaction(consumer = consumer, schema = schema, eventMethod = EventMethod.Update)
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun statusConsumer(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    when (consumer.target!!.value) {
      "LegalPerson" -> statusLegalPerson(consumer = consumer, schema = schema)
      "Employee" -> statusEmployee(consumer = consumer, schema = schema)
      else -> throw Exception("Invalid option.")
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

suspend fun deleteConsumer(consumer: Consumer, schema: String): Any? = newSuspendedTransaction(Dispatchers.IO) {
  runCatching {
    when (consumer.target!!.value) {
      "Person" -> deletePerson(consumer = consumer, schema = schema)
      "Phone" -> deletePhone(consumer = consumer, schema = schema)
      "Address" -> deleteAddress(consumer = consumer, schema = schema)
      else -> throw Exception("Invalid option.")
    }
  }.onFailure {
    it.printStackTrace()
    throw it
  }
}

private suspend fun transaction(consumer: Consumer, schema: String, eventMethod: EventMethod, rollback: Boolean = false) {
  when (eventMethod) {
    EventMethod.Insert -> {
      consumer.person?.let {
        insertPerson(person = it, schema = schema).takeIf { !rollback } ?: deletePerson(consumer = consumer, schema = schema)
      }
      consumer.phone?.let {
        insertPhone(phone = it, schema = schema).takeIf { !rollback } ?: deletePhone(consumer = consumer, schema = schema)
      }
      consumer.legalPerson?.let {
        insertLegalPerson(legalPerson = it, schema = schema).takeIf { !rollback } ?: deleteLegalPerson(consumer = consumer, schema = schema)
      }
      consumer.employee?.let {
        insertEmployee(employee = it, schema = schema).takeIf { !rollback } ?: deleteEmployee(consumer = consumer, schema = schema)
      }
      consumer.address?.let {
        insertAddress(address = it, schema = schema).takeIf { !rollback } ?: deleteAddress(consumer = consumer, schema = schema)
      }
    }
    EventMethod.Update -> {
      consumer.person?.let { updatePerson(person = it, schema = schema) }
      consumer.phone?.let { updatePhone(phone = it, schema = schema) }
      consumer.legalPerson?.let { updateLegalPerson(legalPerson = it, schema = schema) }
      consumer.employee?.let { updateContract(contract = it.contract, schema = schema) }
      consumer.address?.let { updateAddress(address = it, schema = schema) }
    }
    else -> {
    }
  }
}
