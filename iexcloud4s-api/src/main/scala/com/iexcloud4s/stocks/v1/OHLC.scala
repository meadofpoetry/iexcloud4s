package com.iexcloud4s.stocks.v1

final case class OHLC(
  open: OHLC.Data,
  close: OHLC.Data,
  high: BigDecimal,
  low: BigDecimal
)

object OHLC {

  final case class Data(price: BigDecimal, time: Long)

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private implicit val decoderData: Decoder[Data] =
    deriveDecoder[Data]

  private[stocks] implicit val decoder: Decoder[OHLC] =
    deriveDecoder[OHLC]

}
