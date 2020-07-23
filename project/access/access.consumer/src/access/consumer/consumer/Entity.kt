package com.rjdesenvolvimento.aries.access.consumer.consumer

import com.rjdesenvolvimento.aries.access.consumer.address.*
import com.rjdesenvolvimento.aries.access.consumer.employee.*
import com.rjdesenvolvimento.aries.access.consumer.legalperson.*
import com.rjdesenvolvimento.aries.access.consumer.person.*
import com.rjdesenvolvimento.aries.access.consumer.phone.*
import com.rjdesenvolvimento.aries.commons.consumer.entity.*
import java.util.*

data class Consumer(
  override val id: UUID,
  override val active: Boolean,
  var target: TargetClass?,
  val person: Person?,
  val phone: Phone?,
  val legalPerson: LegalPerson?,
  val employee: Employee?,
  val address: Address?
) : BaseEntity()

data class TargetClass(val value: String, val status: Boolean?)
