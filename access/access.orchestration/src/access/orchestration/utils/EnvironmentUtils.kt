package com.rjdesenvolvimento.aries.access.orchestration.utils

import com.typesafe.config.*
import io.ktor.config.*
import io.ktor.util.*

@KtorExperimentalAPI
val ev = HoconApplicationConfig(ConfigFactory.load())
