package com.iexcloud4s

import com.iexcloud4s.http._
import io.circe.Decoder

package object stocks {

  def advancedStats[F[_]](symbol: String)(implicit client: IEXClient[F]): F[AdvancedStats] =
    client.get[AdvancedStats](s"stock/$symbol/advanced-stats", Parameters.empty)

  def company[F[_]](symbol: String)(implicit client: IEXClient[F]): F[Company] =
    client.get[Company](s"stock/$symbol/company", Parameters.empty)

  def delayedQuote[F[_]](symbol: String)(implicit client: IEXClient[F]): F[DelayedQuote] =
    client.get[DelayedQuote](s"stock/$symbol/delayed-quote", Parameters.empty)

  def earnings[F[_]](symbol: String,
    last: Option[Int] = None,
    period: Option[Earnings.Period] = None)
    (implicit client: IEXClient[F]): F[Earnings[Earnings.Data]] = {
    val params = Parameters("last" -> last, "period" -> period)
    client.get[Earnings[Earnings.Data]](s"stock/$symbol/earnings", params)
  }

  def earningsField[F[_], T: Decoder](symbol: String,
    field: Earnings.Field[T],
    last: Int = 1,
    period: Option[Earnings.Period] = None)
    (implicit client: IEXClient[F]): F[T] = {
    val params = Parameters("period" -> period)
    client.get[T](s"stock/$symbol/earnings/$last/${field.string}", params)
  }

  def keyStats[F[_]](symbol: String)(implicit client: IEXClient[F]): F[KeyStats] =
    client.get[KeyStats](s"stock/$symbol/stats", Parameters.empty)

  def keyStats[F[_], T: Decoder](symbol: String, field: KeyStats.Field[T])(implicit client: IEXClient[F]): F[T] =
    client.get[T](s"stock/$symbol/stats/${field.string}", Parameters.empty)

  def largestTrade[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[LargestTrades]] =
    client.get[List[LargestTrades]](s"stock/$symbol/largest-trades", Parameters.empty)
}
