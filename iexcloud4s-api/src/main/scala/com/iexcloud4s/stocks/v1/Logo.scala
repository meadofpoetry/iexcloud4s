package com.iexcloud4s.stocks.v1

final case class Logo(url: String)

object Logo {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[Logo] =
    deriveDecoder[Logo]

}
