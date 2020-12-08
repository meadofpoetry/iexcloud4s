package com.iexcloud4s.stocks

final case class Quote(
  symbol: String,
  companyName: String,
  calculationPrice: Quote.LatestPriceSource,
  open: Option[BigDecimal],
  openTime: Option[Long],
  close: Option[BigDecimal],
  closeTime: Option[Long],
  high: Option[BigDecimal],
  low: Option[BigDecimal],
  latestPrice: BigDecimal,
  latestSource: String,
  latestTime: String,
  latestUpdate: Long,
  latestVolume: Option[BigDecimal],
  volume: Option[BigDecimal],
  iexRealtimePrice: BigDecimal,
  iexRealtimeSize: BigDecimal,
  iexLastUpdated: Long,
  delayedPrice: Option[BigDecimal],
  delayedPriceTime: Option[Long],
  oddLotDelayedPrice: Option[BigDecimal],
  oddLotDelayedPriceTime: Option[Long],
  extendedPrice: Option[BigDecimal],
  extendedChange: Option[BigDecimal],
  extendedChangePercent: Option[BigDecimal],
  extendedPriceTime: Option[Long],
  previousClose: BigDecimal,
  previousVolume: BigDecimal,
  change: BigDecimal,
  changePercent: BigDecimal,
  iexMarketPercent: BigDecimal,
  iexVolume: BigDecimal,
  avgTotalVolume: BigDecimal,
  iexBidPrice: BigDecimal,
  iexBidSize: BigDecimal,
  iexAskPrice: BigDecimal,
  iexAskSize: BigDecimal,
  marketCap: BigDecimal,
  week52High: BigDecimal,
  week52Low: BigDecimal,
  ytdChange: BigDecimal,
  peRatio: BigDecimal,
  lastTradeTime: Long,
  isUSMarketOpen: Boolean
)

object Quote {

  import io.circe.Decoder

  sealed trait LatestPriceSource
  final case object TOPSPrice extends LatestPriceSource
  final case object SIPPrice extends LatestPriceSource
  final case object PreviousClosePrice extends LatestPriceSource
  final case object ClosePrice extends LatestPriceSource
  final case object IEXLastTradePrice extends LatestPriceSource

  object LatestPriceSource {
    def fromString(s: String): Either[String, LatestPriceSource] =
      s match {
        case "tops" => Right(TOPSPrice)
        case "sip" => Right(SIPPrice)
        case "previousclose" => Right(PreviousClosePrice)
        case "close" => Right(ClosePrice)
        case "iexlasttrade" => Right(IEXLastTradePrice)
        case _ => Left("Improper price source " + s)
      }
  }

  sealed abstract class Field[T: Decoder](private[stocks] val string: String) {
    val decoder = implicitly[Decoder[T]]
  }
  final case object Symbol extends Field[String]("symbol")
  final case object CompanyName extends Field[String]("companyName")
  final case object CalculationPrice extends Field[Quote.LatestPriceSource]("calculationPrice")
  final case object Open extends Field[Option[BigDecimal]]("open")
  final case object OpenTime extends Field[Option[Long]]("openTime")
  final case object Close extends Field[Option[BigDecimal]]("close")
  final case object CloseTime extends Field[Option[Long]]("closeTime")
  final case object High extends Field[Option[BigDecimal]]("high")
  final case object Low extends Field[Option[BigDecimal]]("low")
  final case object LatestPrice extends Field[BigDecimal]("latestPrice")
  final case object LatestSource extends Field[String]("latestSource")
  final case object LatestTime extends Field[String]("latestTime")
  final case object LatestUpdate extends Field[Long]("latestUpdate")
  final case object LatestVolume extends Field[Option[BigDecimal]]("latestVolume")
  final case object Volume extends Field[Option[BigDecimal]]("volume")
  final case object IexRealtimePrice extends Field[BigDecimal]("iexRealtimePrice")
  final case object IexRealtimeSize extends Field[BigDecimal]("iexRealtimeSize")
  final case object IexLastUpdated extends Field[Long]("iexLastUpdated")
  final case object DelayedPrice extends Field[Option[BigDecimal]]("delayedPrice")
  final case object DelayedPriceTime extends Field[Option[Long]]("delayedPriceTime")
  final case object OddLotDelayedPrice extends Field[Option[BigDecimal]]("oddLotDelayedPrice")
  final case object OddLotDelayedPriceTime extends Field[Option[Long]]("oddLotDelayedPriceTime")
  final case object ExtendedPrice extends Field[Option[BigDecimal]]("extendedPrice")
  final case object ExtendedChange extends Field[Option[BigDecimal]]("extendedChange")
  final case object ExtendedChangePercent extends Field[Option[BigDecimal]]("extendedChangePercent")
  final case object ExtendedPriceTime extends Field[Option[Long]]("extendedPriceTime")
  final case object PreviousClose extends Field[BigDecimal]("previousClose")
  final case object PreviousVolume extends Field[BigDecimal]("previousVolume")
  final case object Change extends Field[BigDecimal]("change")
  final case object ChangePercent extends Field[BigDecimal]("changePercent")
  final case object IexMarketPercent extends Field[BigDecimal]("iexMarketPercent")
  final case object IexVolume extends Field[BigDecimal]("iexVolume")
  final case object AvgTotalVolume extends Field[BigDecimal]("avgTotalVolume")
  final case object IexBidPrice extends Field[BigDecimal]("iexBidPrice")
  final case object IexBidSize extends Field[BigDecimal]("iexBidSize")
  final case object IexAskPrice extends Field[BigDecimal]("iexAskPrice")
  final case object IexAskSize extends Field[BigDecimal]("iexAskSize")
  final case object MarketCap extends Field[BigDecimal]("marketCap")
  final case object Week52High extends Field[BigDecimal]("week52High")
  final case object Week52Low extends Field[BigDecimal]("week52Low")
  final case object YtdChange extends Field[BigDecimal]("ytdChange")
  final case object PERatio extends Field[BigDecimal]("peRatio")
  final case object LastTradeTime extends Field[Long]("lastTradeTime")
  final case object IsUSMarketOpen extends Field[Boolean]("isUSMarketOpen")

  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private implicit val decoderLatestPriceSource: Decoder[LatestPriceSource] =
    Decoder.decodeString.emap(LatestPriceSource.fromString)

  private[stocks] implicit val decoder: Decoder[Quote] =
    deriveDecoder[Quote]

}
