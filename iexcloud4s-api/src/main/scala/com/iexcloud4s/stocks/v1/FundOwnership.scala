package com.iexcloud4s.stocks.v1

final case class FundOwnership(
  symbol: String,
  id: String,
  adjHolding: BigDecimal,
  adjMv: BigDecimal,
  entityProperName: String,
  report_date: Long,
  reportedHolding: BigDecimal,
  reportedMv: BigDecimal,
  updated: Long
)

object FundOwnership {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

   private[stocks] implicit val decoder: Decoder[FundOwnership] =
     deriveDecoder[FundOwnership]

}
