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
    client.get[List[T]](s"stock/$symbol/chart", params)(Decoder.decodeList(chartCloseOnly.decoder))
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
    client.get[List[T]](s"stock/$symbol/chart/$range", params)(Decoder.decodeList(chartCloseOnly.decoder))
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
    import utils.FormattingSyntax._
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
    client.get[List[T]](s"stock/$symbol/chart/date/${date.chartFormat}", params)(Decoder.decodeList(chartCloseOnly.decoder))
  }

  def incomeStatement[F[_]](
    symbol: String,
    last: Option[Int] = None,
    period: Option[Period] = None)
    (implicit client: IEXClient[F]): F[IncomeStatement] = {
    val params = Parameters("last" -> last, "period" -> period)
    client.get[IncomeStatement](s"stock/$symbol/income", params)
  }

  def insiderRoster[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[InsiderRoster]] =
    client.get[List[InsiderRoster]](s"stock/$symbol/insider-roster", Parameters.empty)

  def insiderSummary[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[InsiderSummary]] =
    client.get[List[InsiderSummary]](s"stock/$symbol/insider-summary", Parameters.empty)

  def insiderTransactions[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[InsiderTransactions]] =
    client.get[List[InsiderTransactions]](s"stock/$symbol/insider-transactions", Parameters.empty)

  def institutionalOwnership[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[InstitutionalOwnership]] =
    client.get[List[InstitutionalOwnership]](s"stock/$symbol/institutional-ownership", Parameters.empty)

  def intradayPrices[F[_]](
    symbol: String,
    chartIEXOnly: Option[Boolean] = None,
    chartReset: Option[Boolean] = None,
    chartSimplify: Option[Boolean] = None,
    chartInterval: Option[Int] = None,
    changeFromClose: Option[Boolean] = None,
    chartLast: Option[Int] = None,
    exactDate: Option[LocalDate] = None,
    chartIEXWhenNull: Option[Boolean] = None)
    (implicit client: IEXClient[F]): F[List[IntradayPrices]] = {
    import utils.FormattingSyntax._
    val params = Parameters(
      "chartIEXOnly" -> chartIEXOnly,
      "chartReset" -> chartReset,
      "chartSimplify" -> chartSimplify,
      "chartInterval" -> chartInterval,
      "changeFromClose" -> changeFromClose,
      "chartLast" -> chartLast,
      "exactDate" -> exactDate.map(_.chartFormat),
      "chartIEXWhenNull" -> chartIEXWhenNull,
    )
    client.get[List[IntradayPrices]](s"stock/$symbol/intraday-prices", params)
  }

  def keyStats[F[_]](symbol: String)(implicit client: IEXClient[F]): F[KeyStats] =
    client.get[KeyStats](s"stock/$symbol/stats", Parameters.empty)

  def keyStatsField[F[_], T](symbol: String, field: KeyStats.Field[T])(implicit client: IEXClient[F]): F[T] =
    client.get[T](s"stock/$symbol/stats/${field.string}", Parameters.empty)(field.decoder)

  def largestTrade[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[LargestTrades]] =
    client.get[List[LargestTrades]](s"stock/$symbol/largest-trades", Parameters.empty)

  def logo[F[_]](symbol: String)(implicit client: IEXClient[F]): F[Logo] =
    client.get[Logo](s"stock/$symbol/logo", Parameters.empty)

  def ohlc[F[_]](symbol: String)(implicit client: IEXClient[F]): F[OHLC] =
    client.get[OHLC](s"stock/$symbol/ohlc", Parameters.empty)

  def ohlcMarket[F[_]](implicit client: IEXClient[F]): F[OHLC] =
    client.get[OHLC](s"stock/market/ohlc", Parameters.empty)

  def peerGroups[F[_]](symbol: String)(implicit client: IEXClient[F]): F[List[String]] =
    client.get[List[String]](s"stock/$symbol/peers", Parameters.empty)

  def previousDayPrice[F[_]](symbol: String)(implicit client: IEXClient[F]): F[Prices] =
    client.get[Prices](s"stock/$symbol/previous", Parameters.empty)

  def priceOnly[F[_]](symbol: String)(implicit client: IEXClient[F]): F[BigDecimal] =
    client.get[BigDecimal](s"stock/$symbol/price", Parameters.empty)

  def priceTarget[F[_]](symbol: String)(implicit client: IEXClient[F]): F[PriceTarget] =
    client.get[PriceTarget](s"stock/$symbol/price-target", Parameters.empty)

}
