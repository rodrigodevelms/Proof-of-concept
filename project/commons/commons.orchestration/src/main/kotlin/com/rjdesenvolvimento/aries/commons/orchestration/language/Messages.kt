@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.orchestration.language

import java.time.*
import java.util.*

fun elkDuplicateTopicError(language: Language): String {
  return when (language) {
    Language.BR -> "Já existe um tópico com esse nome. Ignorando."
    Language.US -> "There is already a topic with that name. Skipping."
  }
}

fun taskSuccessfullyProcessed(language: Language, taskId: UUID): String {
  return when (language) {
    Language.BR -> "Processando a Task com Id: $taskId."
    Language.US -> "Processing the Task with Id: $taskId."
  }
}

fun unprocessedTask(language: Language, taskId: UUID): String {
  return when (language) {
    Language.BR -> " Erro ao processar Task com Id: $taskId."
    Language.US -> "Error processing Task with Id: $taskId."
  }
}

fun notNull(language: Language, fieldName: String): String {
  return when (language) {
    Language.BR -> "$fieldName é obrigatório.".capitalize()
    Language.US -> "$fieldName is required.".capitalize()
  }
}

fun invalidStringLength(language: Language, fieldName: String, minimumValue: Int, maximumValue: Int): String {
  return when (language) {
    Language.BR -> "$fieldName deve conter entre $minimumValue e $maximumValue caracteres.".capitalize()
    Language.US -> "$fieldName must have between $minimumValue and $maximumValue characters.".capitalize()
  }
}

fun invalidField(language: Language, fieldName: String): String {
  return when (language) {
    Language.BR -> "Insira um $fieldName válido."
    Language.US -> "Insert a valid $fieldName."
  }
}

fun invalidDatePeriod(language: Language, fieldName: String, differenceInDays: Long): String {
  return when (language) {
    Language.BR -> "$fieldName inválido. A data deve ser menor ou igual a ${LocalDate.now().minusDays(differenceInDays)} .".capitalize()
    Language.US -> "Invalid $fieldName. The date must be less than or equal to ${LocalDate.now().minusDays(differenceInDays)}."
  }
}

fun invalidDocument(language: Language, fieldName: String): String {
  return when (language) {
    Language.BR -> "$fieldName inválido. Por favor insira um $fieldName válido".capitalize()
    Language.US -> "Invalid $fieldName. Please insert a valid one."
  }
}
