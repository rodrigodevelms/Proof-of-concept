package com.rjdesenvolvimento.aries.access.orchestration.orchestration

import com.rjdesenvolvimento.aries.access.orchestration.settings.*
import com.rjdesenvolvimento.aries.access.orchestration.utils.*
import com.rjdesenvolvimento.aries.commons.kafka.enum.*
import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.service.*
import com.rjdesenvolvimento.aries.commons.orchestration.validations.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import java.util.*

@KtorExperimentalAPI
fun Route.orchestration() {
  route("/api/access/orchestrator") {
    post {
      val taskId = UUID.randomUUID()
      val language = Language.valueOf(
        isnullRequest(
          request = call.request.header("language"),
          isNull = Language.BR.toString()
        )
      )
      runCatching {
        elasticStatus()
        val receive = (call.receive<Orchestration>())
        val validation = orchestrationValidator(
          language = language,
          orchestration = receive
        )
        if (validation.isEmpty()) {
          val id = UUID.randomUUID()
          OrchestrationEvent(
            id = id,
            taskId = taskId,
            eventMethod = EventMethod.Insert,
            room = "room",
            schema = "rjdesenvolvimento",
            message = copyOrchestrator(
              orchestration = receive,
              uuid = id
            )
          ).send()
          val x = OrchestrationEvent(
            id = id,
            taskId = taskId,
            eventMethod = EventMethod.Insert,
            room = "room",
            schema = "rjdesenvolvimento",
            message = copyOrchestrator(
              orchestration = receive,
              uuid = id
            )
          )
          call.respond(
            status = HttpStatusCode.Accepted,
            message = x
//            taskSuccessfullyProcessed(
//              language = language,
//              taskId = taskId
//            )
          )
          orchestrationRepository(
            schema = "rjdesenvolvimento",
            errorMessage = elkDuplicateTopicError(language)
          )
        } else {
          call.respond(
            status = HttpStatusCode.BadRequest,
            message = errorMsg(errorMessages = validation)
          )
        }
      }.onFailure {
        it.printStackTrace()
        call.respond(
          status = HttpStatusCode.InternalServerError,
          message = errorMsg(
            errorMessages = listOf(unprocessedTask(language, taskId))
          )
        )
      }
    }
    put {
      val taskId = UUID.randomUUID()
      val language = Language.valueOf(
        isnullRequest(call.request.header("language"), Language.BR.toString())
      )
      runCatching {
        val receive = call.receive<Orchestration>()
        val validation = orchestrationValidator(language, receive)
        if (validation.isEmpty()) {
          OrchestrationEvent(
            id = receive.id!!,
            taskId = taskId,
            eventMethod = EventMethod.Update,
            room = "room",
            schema = "rjdesenvolvimento",
            message = copyOrchestrator(receive)
          ).send()
          call.respond(
            status = HttpStatusCode.Accepted,
            message = taskSuccessfullyProcessed(language, taskId)
          )
        } else {
          call.respond(
            status = HttpStatusCode.BadRequest,
            message = errorMsg(errorMessages = validation)
          )
        }
      }.onFailure {
        it.printStackTrace()
        call.respond(
          status = HttpStatusCode.InternalServerError,
          message = errorMsg(errorMessages = listOf(unprocessedTask(language, taskId)))
        )
      }
    }
    patch {
      val taskId = UUID.randomUUID()
      val language = Language.valueOf(
        isnullRequest(call.request.header("language"), Language.BR.toString())
      )
      runCatching {
        val receive = call.receive<Orchestration>()
        OrchestrationEvent(
          id = receive.id!!,
          taskId = taskId,
          eventMethod = EventMethod.Status,
          room = "room",
          schema = "rjdesenvolvimento",
          message = receive
        ).send()
        call.respond(
          status = HttpStatusCode.Accepted,
          message = taskSuccessfullyProcessed(language, taskId)
        )
      }.onFailure {
        it.printStackTrace()
        call.respond(
          status = HttpStatusCode.InternalServerError,
          message = errorMsg(errorMessages = listOf(unprocessedTask(language, taskId)))
        )
      }
    }
    delete {
      val taskId = UUID.randomUUID()
      val language = Language.valueOf(
        isnullRequest(call.request.header("language"), Language.BR.toString())
      )
      runCatching {
        val receive = call.receive<Orchestration>()
        OrchestrationEvent(
          id = receive.id!!,
          taskId = taskId,
          eventMethod = EventMethod.Delete,
          room = "room",
          schema = "rjdesenvolvimento",
          message = receive
        ).send()
        call.respond(
          status = HttpStatusCode.Accepted,
          message = taskSuccessfullyProcessed(language, taskId)
        )
      }.onFailure {
        it.printStackTrace()
        call.respond(
          status = HttpStatusCode.InternalServerError,
          message = errorMsg(errorMessages = listOf(unprocessedTask(language, taskId)))
        )
      }
    }
  }
}

fun test(p: PipelineContext<Unit, ApplicationCall>) {
  p.call
}
