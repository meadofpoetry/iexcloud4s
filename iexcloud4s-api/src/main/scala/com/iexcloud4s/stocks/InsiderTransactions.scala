package com.iexcloud4s.stocks

import java.time.LocalDate

final case class InsiderTransactions(
  conversionOrExercisePrice: Option[BigDecimal],
  directIndirect: InsiderTransactions.DirectIndirect,
  effectiveDate: Long,
  filingDate: LocalDate,
  fullName: Option[String],
  is10b51: Boolean,
  postShares: BigDecimal,
  reportedTitle: Option[String],
  symbol: String,
  transactionCode: Char,
  transactionDate: LocalDate,
  transactionPrice: Option[BigDecimal],
  transactionShares: BigDecimal,
  transactionValue: Option[BigDecimal],
  id: String,
  key: String,
  subkey: String,
  updated: Long,
  tranPrice: Option[BigDecimal],
  tranShares: BigDecimal,
  tranValue: Option[BigDecimal]
)

object InsiderTransactions {

  sealed trait DirectIndirect
  final case object Direct extends DirectIndirect
  final case object Indirect extends DirectIndirect

  object DirectIndirect {
    def fromString(s: String): Either[String, DirectIndirect] =
      s match {
        case "D" => Right(Direct)
        case "I" => Right(Indirect)
        case _ => Left("Improper direct-indirect value " + s)
      }
  }

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private implicit val decoderDirectIndirect: Decoder[DirectIndirect] =
    Decoder.decodeString.emap(DirectIndirect.fromString(_))

  private[stocks] implicit val decoder: Decoder[InsiderTransactions] =
    deriveDecoder[InsiderTransactions]

}
