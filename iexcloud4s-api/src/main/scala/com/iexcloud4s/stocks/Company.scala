package com.iexcloud4s.stocks

final case class Company(
  symbol: String,
  companyName: String,
  exchange: String,
  industry: String,
  website: String,
  description: String,
  `CEO`: String,
  securityName: String,
  issueType: String, // TODO proper type
  sector: String,
  primarySicCode: Option[String] = None,
  employees: Long = 0,
  tags: List[String] = Nil,
  address: Option[String] = None,
  address2: Option[String] = None,
  state: Option[String] = None,
  city: Option[String] = None,
  zip: Option[String] = None,
  country: Option[String] = None,
  phone: Option[String] = None,
)

object Company {

  sealed trait IssueType
  final case object AmericanDepositoryReceipt extends IssueType
  final case object RealEstateInvestmentTrust extends IssueType
  final case object ClosedEndFund extends IssueType
  final case object SecondaryIssue extends IssueType
  final case object LimitedPartnerships extends IssueType
  final case object CommonStock extends IssueType
  final case object ExchangeTradedFund extends IssueType
  final case object Warrant extends IssueType
  final case object RightIT extends IssueType
  final case object UnitIT extends IssueType
  final case object Temporary extends IssueType
  final case object Unknown extends IssueType

  object IssueType {
    def fromString(s: String): IssueType =
      s match {
        case "ad" => AmericanDepositoryReceipt
        case "re" => RealEstateInvestmentTrust
        case "ce" => ClosedEndFund
        case "si" => SecondaryIssue
        case "cs" => CommonStock
        case "et" => ExchangeTradedFund
        case "wt" => Warrant
        case "rt" => RightIT
        case "ut" => UnitIT
        case "temp" => Temporary
        case _ => Unknown
      }
  }

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private implicit val decoderIssueType: Decoder[IssueType] =
    Decoder.decodeString.map(s => IssueType.fromString(s))

  private[stocks] implicit val decoder: Decoder[Company] =
    deriveDecoder[Company]

}
