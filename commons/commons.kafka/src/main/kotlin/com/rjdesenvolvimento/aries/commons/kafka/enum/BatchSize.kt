@file:Suppress("unused")

package com.rjdesenvolvimento.aries.commons.kafka.enum

import com.rjdesenvolvimento.aries.commons.kafka.utils.*

enum class BatchSize(val value: Int) {
  Sixteen(SIXTEEN),
  ThirtyTwo(THIRTY_TWO),
  SixtyFour(SIXTY_FOUR),
  OneHundredAndTwentyEight(ONE_HUNDRED_AND_TWENTY_EIGHT);
}

