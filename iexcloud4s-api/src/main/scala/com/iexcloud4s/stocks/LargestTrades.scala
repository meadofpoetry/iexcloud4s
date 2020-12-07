package com.iexcloud4s.stocks

import java.time.LocalTime

final case class LargestTrades(
  price: BigDecimal,
  size: Long,
  time: Long,
  timeLabel: LocalTime,
  venue: String,
  venueName: String
)

object LargestTrades {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[LargestTrades] =
    deriveDecoder[LargestTrades]

}
