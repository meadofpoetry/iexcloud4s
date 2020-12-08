package com.iexcloud4s.stocks.v1

import java.time.LocalDate

object HistoricalPrices {

  final case class CloseOnly(
    close: BigDecimal,
    date: LocalDate,
    volume: BigDecimal,
    changeOverTime: BigDecimal,
    marketChangeOverTime: BigDecimal,
    label: String,
    change: BigDecimal,
    changePercent: BigDecimal
  )

  import io.circe.Decoder

  sealed trait CloseOnlySwitch[T] {
    def toBoolean: Boolean
    def decoder: Decoder[T]
  }
  final case object CloseOnlyFalse extends CloseOnlySwitch[Prices] {
    def toBoolean = false
    def decoder = Prices.decoder
  }
  final case object CloseOnlyTrue extends CloseOnlySwitch[CloseOnly] {
    def toBoolean = true
    def decoder = decoderCloseOnly
  }

  sealed abstract class Range(private[stocks] val string: String)
  final case object Max extends Range("max")
  final case object _5y extends Range("5y")
  final case object _2y extends Range("2y")
  final case object _1y extends Range("1y")
  final case object Ytd extends Range("ytd")
  final case object _6m extends Range("6m")
  final case object _3m extends Range("3m")
  final case object _1m extends Range("1m")
  final case object _1mm extends Range("1mm")
  final case object _5d extends Range("5d")
  final case object _5dm extends Range("5dm")
  final case object Dynamic extends Range("dynamic")

  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoderCloseOnly: Decoder[CloseOnly] =
    deriveDecoder[CloseOnly]

}
