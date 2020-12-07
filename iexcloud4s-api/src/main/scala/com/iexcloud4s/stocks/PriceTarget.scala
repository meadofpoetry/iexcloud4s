package com.iexcloud4s.stocks

import java.time.LocalDate

final case class PriceTarget(
  symbol: String,
  updatedDate: LocalDate,
  priceTargetAverage: BigDecimal,
  priceTargetHigh: BigDecimal,
  priceTargetLow: BigDecimal,
  numberOfAnalysts: Long,
  currency: String
)

object PriceTarget {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[PriceTarget] =
    deriveDecoder[PriceTarget]

}
