package com.iexcloud4s.stocks.v1

import java.time.LocalDate

final case class Financials(
  symbol: String,
  financials: List[Financials.Data]
)

object Financials {

  final case class Data(
    reportDate: LocalDate,
    fiscalDate: LocalDate,
    currency: String,
    grossProfit: BigDecimal,
    costOfRevenue: BigDecimal,
    operatingRevenue: Option[BigDecimal],
    totalRevenue: BigDecimal,
    operatingIncome: BigDecimal,
    netIncome: BigDecimal,
    researchAndDevelopment: BigDecimal,
    operatingExpense: BigDecimal,
    currentAssets: BigDecimal,
    totalAssets: BigDecimal,
    totalLiabilities: BigDecimal,
    currentCash: BigDecimal,
    currentDebt: BigDecimal,
    shortTermDebt: BigDecimal,
    longTermDebt: Option[BigDecimal],
    totalCash: Option[BigDecimal],
    totalDebt: Option[BigDecimal],
    shareholderEquity: BigDecimal,
    cashChange: Option[BigDecimal],
    cashFlow: BigDecimal
  )

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoderFinancialsData: Decoder[Data] =
    deriveDecoder[Data]

  private[stocks] implicit val decoder: Decoder[Financials] =
    deriveDecoder[Financials]

}
