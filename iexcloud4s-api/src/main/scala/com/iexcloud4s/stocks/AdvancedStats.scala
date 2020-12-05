package com.iexcloud4s.stocks

import java.time.LocalDate

final case class AdvancedStats(
  companyName: String,
  marketcap: BigDecimal,
  week52high: BigDecimal,
  week52low: BigDecimal,
  week52change: BigDecimal,
  sharesOutstanding: BigDecimal,
  float: Option[BigDecimal] = None,
  avg10Volume: BigDecimal,
  avg30Volume: BigDecimal,
  day200MovingAvg: BigDecimal,
  day50MovingAvg: BigDecimal,
  employees: BigDecimal,
  ttmEPS: BigDecimal,
  ttmDividendRate: BigDecimal = 0,
  dividendYield: BigDecimal = 0,
  nextDividendDate: Option[LocalDate] = None,
  exDividendDate: Option[LocalDate] = None,
  nextEarningsDate: Option[LocalDate] = None,
  peRatio: BigDecimal,
  beta: BigDecimal,
  maxChangePercent: BigDecimal,
  year5ChangePercent: BigDecimal,
  year2ChangePercent: BigDecimal,
  year1ChangePercent: BigDecimal,
  ytdChangePercent: BigDecimal,
  month6ChangePercent: BigDecimal,
  month3ChangePercent: BigDecimal,
  month1ChangePercent: BigDecimal,
  day30ChangePercent: BigDecimal,
  day5ChangePercent: BigDecimal,
  // Advanced fields
  totalCash: BigDecimal,
  currentDebt: BigDecimal,
  revenue: BigDecimal,
  grossProfit: BigDecimal,
  totalRevenue: BigDecimal,
  `EBITDA`: BigDecimal,
  revenuePerShare: BigDecimal,
  revenuePerEmployee: BigDecimal,
  debtToEquity: BigDecimal,
  profitMargin: BigDecimal,
  enterpriseValue: BigDecimal,
  enterpriseValueToRevenue: BigDecimal,
  priceToSales: BigDecimal,
  priceToBook: BigDecimal,
  forwardPERatio: BigDecimal,
  pegRatio: BigDecimal,
  peHigh: BigDecimal,
  peLow: BigDecimal,
  week52highDate: LocalDate,
  week52lowDate: LocalDate,
  putCallRatio: BigDecimal
)

object AdvancedStats {

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[AdvancedStats] =
    deriveDecoder[AdvancedStats]

}
