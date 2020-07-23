package com.rjdesenvolvimento.aries.commons.orchestration.validations

import com.rjdesenvolvimento.aries.commons.orchestration.language.*
import com.rjdesenvolvimento.aries.commons.orchestration.utils.*

fun validateDocument(language: Language, fieldName: String, document: String): String? {
  return when (onlyNumbers(document).length) {
    0 -> notNull(language,fieldName)
    11 -> {
      if (validateCPF(document)) null
      else invalidDocument(language, "CPF")
    }
    14 -> {
      if (validateCNPJ(document)) null
      else invalidDocument(language, "CNPJ")
    }
    else -> invalidDocument(language, "document number")
  }
}

private fun validateCPF(cpf: String): Boolean {
  val numbers = arrayListOf<Int>()

  cpf.filter { it.isDigit() }.forEach {
    numbers.add(it.toString().toInt())
  }

  if (numbers.size != 11) return false

  verifyEachNumber(numbers, 10)

  //first digit
  val dv1 = ((0..8).sumBy { (it + 1) * numbers[it] }).rem(11).let {
    if (it >= 10) 0 else it
  }

  //second digit
  val dv2 = ((0..8).sumBy { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
    if (it >= 10) 0 else it
  }
  return numbers[9] == dv1 && numbers[10] == dv2
}

private fun validateCNPJ(cnpj: String): Boolean {

  val numbers = arrayListOf<Int>()
  cnpj.filter { it.isDigit() }.forEach {
    numbers.add(it.toString().toInt())
  }
  if (numbers.size != 14) return false

  verifyEachNumber(numbers, 14)

  //first digit
  val dv1 = 11 - (arrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2).mapIndexed { index, i ->
    i * numbers[index]
  }).sum().rem(11)
  numbers.add(dv1)

  //second digit
  val dv2 = 11 - (arrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2).mapIndexed { index, i ->
    i * numbers[index]
  }).sum().rem(11)

  return numbers[12] == dv1 && numbers[13] == dv2
}

private fun verifyEachNumber(numbers: List<Int>, number: Int): Boolean {
  (0..9).forEach { n ->
    val digits = arrayListOf<Int>()
    (0..number).forEach { _ -> digits.add(n) }
    if (numbers == digits) return false
  }
  return true
}
