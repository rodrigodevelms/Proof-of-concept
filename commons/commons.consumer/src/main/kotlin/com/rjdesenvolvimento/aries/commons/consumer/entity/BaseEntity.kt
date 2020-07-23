package com.rjdesenvolvimento.aries.commons.consumer.entity

import com.rjdesenvolvimento.aries.commons.consumer.`interface`.*
import java.util.*


@Suppress("unused")
abstract class BaseEntity : IEntity {
  abstract val id: UUID
  abstract val active: Boolean
}
