package com.iexcloud4s.stocks.v1

import java.time.LocalDate

final case class Splits(
  exDate: LocalDate,
  declaredDate: Option[LocalDate],
  ratio: BigDecimal,
  toFactor: BigDecimal,
  fromFactor: BigDecimal,
  description: String
)

object Splits {

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

  private[stocks] implicit val decoder: Decoder[Splits] =
    deriveDecoder[Splits]

}
