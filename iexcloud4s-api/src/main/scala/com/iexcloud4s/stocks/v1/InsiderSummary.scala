package com.iexcloud4s.stocks.v1

final case class InsiderSummary(
  fullName: String,
  netTransacted: BigDecimal,
  reportedTitle: Option[String],
  totalBought: Option[BigDecimal],
  totalSold: Option[BigDecimal]
)

object InsiderSummary {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[InsiderSummary] =
    deriveDecoder[InsiderSummary]

}
