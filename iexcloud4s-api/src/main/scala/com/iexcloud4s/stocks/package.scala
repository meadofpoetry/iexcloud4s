package com.iexcloud4s

import com.iexcloud4s.http._
import com.iexcloud4s.types.{Period, SortingOrder }
import io.circe.Decoder
import java.time.LocalDate

package object stocks {

  def advancedStats[F[_]](symbol: String)(implicit client: IEXClient[F]): F[AdvancedStats] =
    client.get[AdvancedStats](s"stock/$symbol/advanced-stats", Parameters.empty)

  def company[F[_]](symbol: String)(implicit client: IEXClient[F]): F[Company] =
    client.get[Company](s"stock/$symbol/company", Parameters.empty)

  def delayedQuote[F[_]](symbol: String)(implicit client: IEXClient[F]): F[DelayedQuote] =
    client.get[DelayedQuote](s"stock/$symbol/delayed-quote", Parameters.empty)

  def earnings[F[_]](symbol: String,
    last: Option[Int] = None,
    period: Option[Period] = None)
    (implicit client: IEXClient[F]): F[Earnings] = {
    val params = Parameters("last" -> last, "period" -> period)
    client.get[Earnings](s"stock/$symbol/earnings", params)
  }

  def earningsField[F[_], T](symbol: String,
    field: Earnings.Field[T],
    last: Int = 1,
    period: Option[Period] = None)
    (implicit client: IEXClient[F]): F[T] = {
    val params = Parameters("period" -> period)
    client.get[T](s"stock/$symbol/earnings/$last/${field.string}", params)(field.decoder)
  }

  def estimates[F[_]](symbol: String,
    last: Option[Int] = None,
    period: Option[Period] = None)
    (implicit client: IEXClient[F]): F[Estimates] = {
    val params = Parameters("last" -> last, "period" -> period)
    client.get[Estimates](s"stock/$symbol/estimates", params)
  }

  def financials[F[_]](symbol: String,
    period: Option[Period] = None)
    (implicit client: IEXClient[F]): F[Financials] = {
    val params = Parameters("period" -> period)
    client.get[Financials](s"stock/$symbol/financials", params)
  }

  def fundOwnership[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[FundOwnership]] =
    client.get[List[FundOwnership]](s"stock/$symbol/fund-ownership", Parameters.empty)

  def historicalPricesChart[F[_], T](
    symbol: String,
    chartCloseOnly: HistoricalPrices.CloseOnlySwitch[T] =
      HistoricalPrices.CloseOnlyFalse,
    chartSimplify: Option[Boolean] = None,
    chartInterval: Option[Int] = None,
    changeFromClose: Option[Boolean] = None,
    chartLast: Option[Int] = None,
    sort: Option[SortingOrder] = None,
    includeToday: Option[Boolean] = None)
    (implicit client: IEXClient[F]): F[List[T]] = {
    val params = Parameters(
      "chartSimplify" -> chartSimplify,
      "chartInterval" -> chartInterval,
      "changeFromClose" -> changeFromClose,
      "chartLast" -> chartLast,
      "sort" -> sort,
      "includeToday" -> includeToday,
      "chartCloseOnly" -> Some(chartCloseOnly.toBoolean)
    )
    client.get[List[T]](s"stock/$symbol/chart", Parameters.empty)(Decoder.decodeList(chartCloseOnly.decoder))
  }

  def historicalPricesChartRange[F[_], T](
    symbol: String,
    range: HistoricalPrices.Range,
    chartCloseOnly: HistoricalPrices.CloseOnlySwitch[T] =
      HistoricalPrices.CloseOnlyFalse,
    chartSimplify: Option[Boolean] = None,
    chartInterval: Option[Int] = None,
    changeFromClose: Option[Boolean] = None,
    chartLast: Option[Int] = None,
    sort: Option[SortingOrder] = None,
    includeToday: Option[Boolean] = None)
    (implicit client: IEXClient[F]): F[List[T]] = {
    val params = Parameters(
      "chartSimplify" -> chartSimplify,
      "chartInterval" -> chartInterval,
      "changeFromClose" -> changeFromClose,
      "chartLast" -> chartLast,
      "sort" -> sort,
      "includeToday" -> includeToday,
      "chartCloseOnly" -> Some(chartCloseOnly.toBoolean)
    )
    client.get[List[T]](s"stock/$symbol/chart/$range", Parameters.empty)(Decoder.decodeList(chartCloseOnly.decoder))
  }

  def historicalPricesChartDate[F[_], T](
    symbol: String,
    date: LocalDate,
    chartCloseOnly: HistoricalPrices.CloseOnlySwitch[T] =
      HistoricalPrices.CloseOnlyFalse,
    chartByDay: Option[Boolean] = None,
    chartSimplify: Option[Boolean] = None,
    chartInterval: Option[Int] = None,
    changeFromClose: Option[Boolean] = None,
    chartLast: Option[Int] = None,
    sort: Option[SortingOrder] = None,
    includeToday: Option[Boolean] = None)
    (implicit client: IEXClient[F]): F[List[T]] = {
    import HistoricalPrices._
    val params = Parameters(
      "chartByDay" -> chartByDay,
      "chartSimplify" -> chartSimplify,
      "chartInterval" -> chartInterval,
      "changeFromClose" -> changeFromClose,
      "chartLast" -> chartLast,
      "sort" -> sort,
      "includeToday" -> includeToday,
      "chartCloseOnly" -> Some(chartCloseOnly.toBoolean)
    )
    client.get[List[T]](s"stock/$symbol/chart/date/${date.chartFormat}", Parameters.empty)(Decoder.decodeList(chartCloseOnly.decoder))
  }

  def keyStats[F[_]](symbol: String)(implicit client: IEXClient[F]): F[KeyStats] =
    client.get[KeyStats](s"stock/$symbol/stats", Parameters.empty)

  def keyStatsField[F[_], T](symbol: String, field: KeyStats.Field[T])(implicit client: IEXClient[F]): F[T] =
    client.get[T](s"stock/$symbol/stats/${field.string}", Parameters.empty)(field.decoder)

  def largestTrade[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[LargestTrades]] =
    client.get[List[LargestTrades]](s"stock/$symbol/largest-trades", Parameters.empty)
}
