package com.iexcloud4s.stocks.v1

import java.time.LocalDate
import java.time.LocalTime

final case class IntradayPrices(
  date: LocalDate,
  minute: LocalTime,
  label: String,
  // marketOpen: BigDecimal,
  // marketClose: BigDecimal,
  // marketHigh: BigDecimal,
  // marketLow: BigDecimal,
  // marketAverage: BigDecimal,
  // marketVolume: BigDecimal,
  // marketNotional: BigDecimal,
  // marketNumberOfTrades: BigDecimal,
  // marketChangeOverTime: BigDecimal,
  high: Option[BigDecimal],
  low: Option[BigDecimal],
  open: Option[BigDecimal],
  close: Option[BigDecimal],
  average: Option[BigDecimal],
  volume: BigDecimal,
  notional: BigDecimal,
  numberOfTrades: BigDecimal,
  //changeOverTime: BigDecimal
)

object IntradayPrices {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[IntradayPrices] =
    deriveDecoder[IntradayPrices]

}
