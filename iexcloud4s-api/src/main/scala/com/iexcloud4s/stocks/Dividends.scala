package com.iexcloud4s.stocks

import java.time.LocalDate

final case class Dividends(
  symbol: String,
  exDate: LocalDate,
  paymentDate: LocalDate,
  recordDate: LocalDate,
  declaredDate: LocalDate,
  amount: BigDecimal,
  flag: String,
  currency: String,
  description: String,
  frequency: String
)

object Dividends {

  sealed abstract class Range(private val string: String) {
    override def toString(): String = string
  }
  final case object _5y extends Range("5y")
  final case object _2y extends Range("2y")
  final case object _1y extends Range("1y")
  final case object Ytd extends Range("ytd")
  final case object _6m extends Range("6m")
  final case object _3m extends Range("3m")
  final case object _1m extends Range("1m")
  final case object Next extends Range("next")

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[Dividends] =
    deriveDecoder[Dividends]

}
