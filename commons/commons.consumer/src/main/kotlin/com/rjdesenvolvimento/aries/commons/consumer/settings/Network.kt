package com.rjdesenvolvimento.aries.commons.consumer.settings

import com.typesafe.config.*
import com.zaxxer.hikari.*
import io.ktor.config.*
import io.ktor.util.*
import org.flywaydb.core.*
import org.jetbrains.exposed.sql.*

private const val MAXIMUM_POOL_SIZE = 3

@KtorExperimentalAPI
@Suppress("unused")
object Network {
  private val appConfig = HoconApplicationConfig(ConfigFactory.load())
  private val dbUrl = appConfig.property("db.url").getString()
  private val dbDriver = appConfig.property("db.driver").getString()
  private val dbUser = appConfig.property("db.user").getString()
  private val dbPassword = appConfig.property("db.password").getString()

  fun startNetWork() {
    Database.connect(hikari())
    val flyway = Flyway.configure().dataSource(
      dbUrl,
      dbUser,
      dbPassword
    ).load()
    flyway.migrate()
  }

  private fun hikari(): HikariDataSource {
    val config = HikariConfig()
    config.jdbcUrl = dbUrl
    config.driverClassName = dbDriver
    config.username = dbUser
    config.password = dbPassword
    config.maximumPoolSize = MAXIMUM_POOL_SIZE
    config.isAutoCommit = false
    config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    config.validate()
    return HikariDataSource(config)
  }
}
