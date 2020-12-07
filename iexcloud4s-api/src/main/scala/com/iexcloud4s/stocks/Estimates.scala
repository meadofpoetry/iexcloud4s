package com.iexcloud4s.stocks

import java.time.LocalDate

final case class Estimates(
  symbol: String,
  estimates: List[Estimates.Data]
)

object Estimates {

  sealed trait AnnounceTime
  final case object BeforeTheOpen extends AnnounceTime
  final case object AfterMarketClose extends AnnounceTime
  final case object DuringMarket extends AnnounceTime

  object AnnounceTime {
    def fromString(s: String): Option[AnnounceTime] =
      s match {
        case "BTO" => Some(BeforeTheOpen)
        case "AMC" => Some(AfterMarketClose)
        case "DMT" => Some(DuringMarket)
        case _ => None
      }
  }

  final case class Data(
    consensusEPS: BigDecimal,
    announceTime: Option[AnnounceTime],
    numberOfEstimates: BigDecimal,
    reportDate: Option[LocalDate],
    fiscalPeriod: String,
    fiscalEndDate: LocalDate,
    currency: String
  )

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoderAnnounceTime: Decoder[Option[AnnounceTime]] =
    Decoder.decodeOption(Decoder.decodeString).map(_.flatMap(AnnounceTime.fromString(_)))

  private[stocks] implicit val decoderEarningsData: Decoder[Data] =
    deriveDecoder[Data]

  private[stocks] implicit val decoder: Decoder[Estimates] =
    deriveDecoder[Estimates]

}
