# [WIP] iexcloud4s

An idiomatic functional `cats-effect` compatible bindings to [IEX Cloud API](https://iexcloud.io/docs/api/) for Scala (2.13 yet).

### Examples

```
import com.iexcloud4s.http._
import com.iexcloud4s.stocks.v1._

import cats.effect._
import scala.concurrent.ExecutionContext.Implicits.global

implicit val contextShift: ContextShift[IO] = IO.contextShift(ExecutionContext.global)

def makeClient(token: String): Resource[IO, IEXClient[IO]] =
  ClientBuilder
    .apply[IO](global)
    .withToken(token)
    .resource
    
def stockPreviousDayPriceChange(stock: String)(implicit client: IEXClient[IO]): IO[BigDecimal] =
  previousDayPrice(stock).map(_.change)
```

Some APIs also allow to retrieve separate data columns, iexcloud4s utilizes GADT to perform that in a typesafe manner.

```
def stockPERatio(stock: String)(implicit client: IEXClient[IO]): IO[BigDecimal] =
  keyStatsField(stock, KeyStats.PERatio)
  
final case class PriceChangeAndPE(priceChange: BigDecimal, peRatio: BigDecimal)

def stockPriceChangeAndPE(token: String, stock: String): IO[PriceChangeAndPE] =
  makeClient(token).use { implicit client =>
    for {
      priceChange <- stockPreviousDayPriceChange(stock)
      peRatio <- stockPERatio(stock)
    } yield PriceChangeAndPE(priceChange, peRatio)
  }
```

### TODO
- [ ] Spark Dataset support
- [ ] Time series
- [x] Stocks / Equities
- [ ] Corporate Actions
- [ ] Market Info
- [ ] News
- [ ] Cryptocurrency
- [ ] Forex / Currencies
- [ ] Options
- [ ] Treasuries
- [ ] Commodities
- [ ] Economic Data
