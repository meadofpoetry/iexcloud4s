package com.iexcloud4s.stocks

final case class InsiderSummary(
  fullName: String,
  netTransacted: BigDecimal,
  reportedTitle: String,
  totalBought: BigDecimal,
  totalSold: BigDecimal
)

object InsiderSummary {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[InsiderSummary] =
    deriveDecoder[InsiderSummary]

}
