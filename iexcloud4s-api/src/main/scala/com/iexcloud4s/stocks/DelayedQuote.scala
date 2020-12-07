package com.iexcloud4s.stocks

final case class DelayedQuote(
  symbol: String,
  delayedPrice: BigDecimal,
  delayedSize: Long,
  delayedPriceTime: Long,
  high: BigDecimal,
  low: BigDecimal,
  totalVolume: Long,
  processedTime: Long
)

object DelayedQuote {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[DelayedQuote] =
    deriveDecoder[DelayedQuote]

}
