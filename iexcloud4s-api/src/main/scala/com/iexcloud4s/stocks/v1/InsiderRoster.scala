package com.iexcloud4s.stocks.v1

final case class InsiderRoster(
  entityName: String,
  reportDate: Long,
  position: BigDecimal
)

object InsiderRoster {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[InsiderRoster] =
    deriveDecoder[InsiderRoster]

}
