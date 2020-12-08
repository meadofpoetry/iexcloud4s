package com.iexcloud4s.stocks

import java.time.LocalDate

final case class VolumeByVenue(
  volume: BigDecimal,
  venue: String,
  venueName: String,
  date: LocalDate,
  marketPercent: BigDecimal,
)

object VolumeByVenue {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[VolumeByVenue] =
    deriveDecoder[VolumeByVenue]

}
