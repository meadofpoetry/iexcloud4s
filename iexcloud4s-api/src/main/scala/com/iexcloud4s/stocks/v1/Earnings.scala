package com.iexcloud4s.stocks.v1

import java.time.LocalDate

final case class Earnings(
  symbol: String,
  earnings: List[Earnings.Data]
)

object Earnings {

  final case class Data(
    actualEPS: BigDecimal,
    consensusEPS: BigDecimal,
    announceTime: String,
    numberOfEstimates: Long,
    `EPSSurpriseDollar`: BigDecimal,
    `EPSReportDate`: LocalDate,
    fiscalPeriod: String,
    fiscalEndDate: LocalDate,
    yearAgo: BigDecimal,
    yearAgoChangePercent: BigDecimal
  )

  import io.circe.Decoder

  sealed abstract class Field[T: Decoder](private[stocks] val string: String) {
    val decoder = implicitly[Decoder[T]]
  }
  final case object ActualEPS extends Field[BigDecimal]("actualEPS")
  final case object ConsensusEPS extends Field[BigDecimal]("consensusEPS")
  final case object AnnounceTime extends Field[BigDecimal]("announceTime")
  final case object NumberOfEstimates extends Field[BigDecimal]("numberOfEstimates")
  final case object EPSSurpriseDollar extends Field[BigDecimal]("EPSSurpriseDollar")
  final case object EPSReportDate extends Field[BigDecimal]("EPSReportDate")
  final case object FiscalPeriod extends Field[BigDecimal]("fiscalPeriod")
  final case object FiscalEndDate extends Field[BigDecimal]("fiscalEndDate")
  final case object YearAgo extends Field[BigDecimal]("yearAgo")
  final case object YearAgoChangePercent extends Field[BigDecimal]("yearAgoChangePercent")

  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoderEarningsData: Decoder[Data] =
    deriveDecoder[Data]

  private[stocks] implicit val decoder: Decoder[Earnings] =
    deriveDecoder[Earnings]

}
