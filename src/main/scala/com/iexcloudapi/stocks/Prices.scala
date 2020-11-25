package com.iexcloudapi.stocks

import zio._
import io.circe.generic.auto._
import com.iexcloudapi.http.HttpClient._

object Prices {

  def quote(symbol: String): RIO[HttpClient, Map[String,String]] =
    get[Map[String,String]]("stock/" + symbol + "/quote", Map.empty)

}
