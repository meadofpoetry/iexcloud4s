package com.iexcloud4s.stocks.v1

import java.time.LocalDate

final case class InstitutionalOwnership(
  symbol: String,
  id: String,
  adjHolding: BigDecimal,
  adjMv: BigDecimal,
  entityProperName: String,
  reportDate: Long,
  filingDate: LocalDate,
  reportedHolding: BigDecimal,
  updated: Long
)

object InstitutionalOwnership {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[InstitutionalOwnership] =
    deriveDecoder[InstitutionalOwnership]

}
