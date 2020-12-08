package com.iexcloud4s.stocks.v1

import java.time.LocalDate

final case class Prices(
  symbol: String,
  close: BigDecimal,
  high: BigDecimal,
  low: BigDecimal,
  open: BigDecimal,
  volume: BigDecimal,
  id: String,
  key: String,
  subkey: String,
  date: LocalDate,
  updated: Long,
  changeOverTime: BigDecimal,
  marketChangeOverTime: BigDecimal,
  uOpen: BigDecimal,
  uClose: BigDecimal,
  uHigh: BigDecimal,
  uLow: BigDecimal,
  uVolume: BigDecimal,
  fOpen: BigDecimal,
  fClose: BigDecimal,
  fHigh: BigDecimal,
  fLow: BigDecimal,
  fVolume: BigDecimal,
  label: String,
  change: BigDecimal,
  changePercent: BigDecimal
)

object Prices {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[Prices] =
    deriveDecoder[Prices]

}
