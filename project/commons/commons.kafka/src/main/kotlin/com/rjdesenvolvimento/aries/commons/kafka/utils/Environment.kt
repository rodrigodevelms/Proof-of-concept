package com.rjdesenvolvimento.aries.commons.kafka.utils

import io.ktor.config.*
import io.ktor.util.*


@KtorExperimentalAPI
fun environmentVariable(hoconApplicationConfig: HoconApplicationConfig, variable: String): String {
  return hoconApplicationConfig.property(variable).getString()
}
