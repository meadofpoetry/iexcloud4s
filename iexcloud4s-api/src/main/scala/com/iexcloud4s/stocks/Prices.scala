package com.iexcloud4s.stocks

import io.circe.Decoder
import com.iexcloud4s.http.HttpClient._

object Prices {

  def quote[F[_]](symbol: String)(implicit client: Service[F]): F[Map[String,String]] =
    client.get[Map[String,String]]("stock/" + symbol + "/quote", Map.empty)

}
