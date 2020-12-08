package com.iexcloud4s.stocks

final case class Book(
  quote: Quote,
  bids: List[Book.Offer],
  asks: List[Book.Offer],
  trades: List[Book.Trades],
  systemEvent: Book.SystemEvent
)

object Book {

  // TODO move to DEEP
  final case class Offer(price: BigDecimal, size: BigDecimal, timestamp: Long)
  final case class SystemEvent(systemEvent: String, timestamp: Long)
  final case class Trades(
    price: BigDecimal,
    size: BigDecimal,
    tradeId: Long,
    isISO: Boolean,
    isOddLot: Boolean,
    isOutsideRegularHours: Boolean,
    isSinglePriceCross: Boolean,
    isTradeThroughExempt: Boolean,
    timestamp: Long
  )

  import io.circe.Decoder
  import com.iexcloud4s.utils.Decoding._
  import io.circe.generic.extras.semiauto.deriveDecoder

  private implicit val decoderOffer: Decoder[Offer] =
    deriveDecoder[Offer]

  private implicit val decoderTrades: Decoder[Trades] =
    deriveDecoder[Trades]

  private implicit val decoderSystemEvent: Decoder[SystemEvent] =
    deriveDecoder[SystemEvent]

  private[stocks] implicit val decoder: Decoder[Book] =
    deriveDecoder[Book]

}
