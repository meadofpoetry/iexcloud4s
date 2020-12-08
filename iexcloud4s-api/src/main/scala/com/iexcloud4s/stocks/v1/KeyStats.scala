package com.iexcloud4s.stocks.v1

import java.time.LocalDate

final case class KeyStats(
  companyName: String,
  marketcap: BigDecimal,
  week52high: BigDecimal,
  week52low: BigDecimal,
  week52change: BigDecimal,
  sharesOutstanding: BigDecimal,
  float: Option[BigDecimal],
  avg10Volume: BigDecimal,
  avg30Volume: BigDecimal,
  day200MovingAvg: BigDecimal,
  day50MovingAvg: BigDecimal,
  employees: BigDecimal,
  ttmEPS: BigDecimal,
  ttmDividendRate: BigDecimal,
  dividendYield: BigDecimal,
  nextDividendDate: Option[LocalDate],
  exDividendDate: LocalDate,
  nextEarningsDate: Option[LocalDate],
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
  day5ChangePercent: BigDecimal
)

object KeyStats {

  import io.circe.Decoder

  sealed abstract class Field[T: Decoder](private[stocks] val string: String) {
    val decoder = implicitly[Decoder[T]]
  }
  final case object CompanyName extends Field[String]("companyName")
  final case object Marketcap extends Field[BigDecimal]("marketcap")
  final case object Week52high extends Field[BigDecimal]("week52high")
  final case object Week52low extends Field[BigDecimal]("week52low")
  final case object Week52change extends Field[BigDecimal]("week52change")
  final case object SharesOutstanding extends Field[BigDecimal]("sharesOutstanding")
  final case object Float extends Field[Option[BigDecimal]]("float")
  final case object Avg10Volume extends Field[BigDecimal]("avg10Volume")
  final case object Avg30Volume extends Field[BigDecimal]("avg30Volume")
  final case object Day200MovingAvg extends Field[BigDecimal]("day200MovingAvg")
  final case object Day50MovingAvg extends Field[BigDecimal]("day50MovingAvg")
  final case object Employees extends Field[BigDecimal]("employees")
  final case object TtmEPS extends Field[BigDecimal]("ttmEPS")
  final case object TtmDividendRate extends Field[BigDecimal]("ttmDividendRate")
  final case object DividendYield extends Field[BigDecimal]("dividendYield")
  final case object NextDividendDate extends Field[Option[LocalDate]]("nextDividendDate")
  final case object ExDividendDate extends Field[LocalDate]("exDividendDate")
  final case object NextEarningsDate extends Field[Option[LocalDate]]("nextEarningsDate")
  final case object PERatio extends Field[BigDecimal]("peRatio")
  final case object Beta extends Field[BigDecimal]("beta")
  final case object MaxChangePercent extends Field[BigDecimal]("maxChangePercent")
  final case object Year5ChangePercent extends Field[BigDecimal]("year5ChangePercent")
  final case object Year2ChangePercent extends Field[BigDecimal]("year2ChangePercent")
  final case object Year1ChangePercent extends Field[BigDecimal]("year1ChangePercent")
  final case object YtdChangePercent extends Field[BigDecimal]("ytdChangePercent")
  final case object Month6ChangePercent extends Field[BigDecimal]("month6ChangePercent")
  final case object Month3ChangePercent extends Field[BigDecimal]("month3ChangePercent")
  final case object Month1ChangePercent extends Field[BigDecimal]("month1ChangePercent")
  final case object Day30ChangePercent extends Field[BigDecimal]("day30ChangePercent")
  final case object Day5ChangePercent extends Field[BigDecimal]("day5ChangePercent")

  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private[stocks] implicit val decoder: Decoder[KeyStats] =
    deriveDecoder[KeyStats]

}
