package com.rjdesenvolvimento.aries.access.orchestration.orchestration

import com.rjdesenvolvimento.aries.access.orchestration.settings.*
import org.elasticsearch.*
import org.elasticsearch.client.*

fun orchestrationRepository(schema: String, errorMessage: String) {
  runCatching {
    createOrchestrationRepository(schema).createIndex {
      configure {
        settings {
          replicas = 1
          shards = 1
        }
        mappings {
          objField("person") {
            text("id")
            bool("active")
            text("name")
            text("email")
            text("document")
            text("birthdate")
            text("maritalStatus")
            text("gender")
            objField("phones") {
              text("id")
              bool("active")
              text("idd")
              text("ddd")
              text("number")
              text("personFk")
            }
          }
          objField("legalPerson") {
            text("id")
            bool("active")
            text("companyName")
            text("fancyName")
            text("document")
            text("openingDate")
            text("legalNature")
            text("lineOfBusiness")
            text("personFk")
          }
          objField("employee") {
            text("id")
            bool("active")
            objField("contract") {
              text("id")
              bool("active")
              text("workRegime")
              text("hiringDate")
              text("resignationDate")
              text("remuneration")
              text("role")
              text("workday")
              text("employeeFk")
            }
            text("personFk")
          }
          objField("address") {
            text("id")
            bool("active")
            text("country")
            text("state")
            text("city")
            text("zipCode")
            text("district")
            text("publicPlace")
            text("number")
            text("complement")
            text("personFk")
          }
        }
      }
    }
  }.onFailure {
    when (it) {
      is ElasticsearchStatusException -> println(errorMessage)
      else -> {
        it.printStackTrace()
      }
    }
  }
}

