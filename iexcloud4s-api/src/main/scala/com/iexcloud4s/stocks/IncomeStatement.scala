package com.iexcloud4s.stocks

import java.time.LocalDate

final case class IncomeStatement(
  symbol: String,
  income: List[IncomeStatement.Data]
)

object IncomeStatement {

  final case class Data(
    reportDate: LocalDate,
    fiscalDate: LocalDate,
    currency: String,
    totalRevenue: BigDecimal,
    costOfRevenue: BigDecimal,
    grossProfit: BigDecimal,
    researchAndDevelopment: BigDecimal,
    sellingGeneralAndAdmin: BigDecimal,
    operatingExpense: BigDecimal,
    operatingIncome: BigDecimal,
    otherIncomeExpenseNet: BigDecimal,
    ebit: BigDecimal,
    interestIncome: BigDecimal,
    pretaxIncome: BigDecimal,
    incomeTax: BigDecimal,
    minorityInterest: BigDecimal,
    netIncome: BigDecimal,
    netIncomeBasic: BigDecimal
  )

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoderIncomeStatementData: Decoder[Data] =
    deriveDecoder[Data]

  private[stocks] implicit val decoder: Decoder[IncomeStatement] =
    deriveDecoder[IncomeStatement]

}
