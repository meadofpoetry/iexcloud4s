package com.iexcloud4s.stocks

import java.time.LocalDate

final case class Earnings[T](
  symbol: String,
  earnings: List[T]
)

object Earnings {

  sealed abstract class Period(private[stocks] val string: String)
  final case object Annual extends Period("annual")
  final case object Quarter extends Period("quarter")

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

  sealed abstract class Field[T](private[stocks] val string: String)
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

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoderEarningsData: Decoder[Data] =
    deriveDecoder[Data]

  private[stocks] implicit def decoder[T: Decoder]: Decoder[Earnings[T]] =
    deriveDecoder[Earnings[T]]

}
