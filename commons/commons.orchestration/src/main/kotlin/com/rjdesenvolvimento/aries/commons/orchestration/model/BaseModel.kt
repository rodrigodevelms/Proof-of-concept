package com.rjdesenvolvimento.aries.commons.orchestration.model

import com.rjdesenvolvimento.aries.commons.orchestration.`interface`.*
import java.util.*

abstract class BaseModel : IModel {
  abstract val id: UUID?
  abstract val active: Boolean
}
