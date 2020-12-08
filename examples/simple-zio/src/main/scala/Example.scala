package com.iexcloudapi

import zio._
import zio.clock._
import zio.console.{Console, putStrLn}
import zio.interop.catz._
import zio.logging.Logging

import com.iexcloud4s.http._
import com.iexcloud4s.stocks._
import com.iexcloud4s.types._

import scala.concurrent.ExecutionContext.Implicits
import org.http4s.util.execution
import java.util.concurrent.Delayed

object Main extends App {

  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = {
    val execution =
      makeClient(args(0), true).flatMap { client =>
        program.provideSomeLayer[ZEnv](client.toLayer)
      }

    execution.foldM(
      e => putStrLn(s"Execution failed with: ${e.getMessage()}\n${e.printStackTrace()}") *> ZIO.succeed(ExitCode.failure),
      _ => ZIO.succeed(ExitCode.success)
    )
  }

  type Http = Has[IEXClient[Task]]

  private object Http {
    def call[E,R](f: IEXClient[Task] => ZIO[Http, E, R]): ZIO[Http, E, R] =
      ZIO.accessM(s => f(s.get))
  }

  private def makeClient(token: String, sandboxed: Boolean): UIO[TaskManaged[IEXClient[Task]]] =
    ZIO.runtime[Any].map { implicit rts =>
      ClientBuilder
        .apply[Task](Implicits.global)
        .withToken(token)
        .withSandboxed(sandboxed)
        .resource
        .toManaged
    }

  private def program: RIO[Http with Console with Clock, Unit] =
    RIO.access[Http](_.get).flatMap { implicit service =>
      for {
        stats <- keyStats("IBM")
        _ <- putStrLn("IBM Key Stats: " + stats)
        amdBeta <- keyStatsField("AMD", KeyStats.Beta)
        _ <- putStrLn("AMD Beta: " + amdBeta)
        advStats <- advancedStats("AMD")
        _ <- putStrLn("AMD adv: " + advStats)
        comp <- company("XLNX")
        _ <- putStrLn("XLNX info: " + comp)
        earn <- earnings("AMD")
        _ <- putStrLn("AMD earn: " + earn)
        earnea <- earningsField("AMD", Earnings.YearAgo)
        _ <- putStrLn("AMD earn year ago: " + earnea)
        estims <- estimates("AMD", period = Some(Annual))
        _ <- putStrLn("AMD est: " + estims)
        fin <- financials("AMD")
        _ <- putStrLn("AMD financials: " + fin)
        fown <- fundOwnership("AMD")
        _ <- putStrLn("AMD fund ownership: " + fown)
        hist <- historicalPricesChart("AMD")
        _ <- putStrLn("AMD histprices chart: " + hist)
        income <- incomeStatement("AMD", last=Some(10))
        _ <- putStrLn("AMD income: " + income)
        insros <- insiderRoster("AMD")
        _ <- putStrLn("AMD insider roster: " + insros)
        inssum <- insiderSummary("AMD")
        _ <- putStrLn("AMD insider summary: " + inssum)
        instran <- insiderTransactions("AMD")
        _ <- putStrLn("AMD insider transactions: " + instran)
        insown <- institutionalOwnership("AMD")
        _ <- putStrLn("AMD institutional ownership: " + insown)
        intra <- intradayPrices("AMD")
        _ <- putStrLn("AMD intraday prices: " + intra)
        //oh <- ohlc("AMD")
        //_ <- putStrLn("AMD OHLC: " + oh)
        peers <- peerGroups("AMD")
        _ <- putStrLn("AMD peers: " + peers)
        prev <- previousDayPrice("AMD")
        _ <- putStrLn("AMD yest price: " + prev)
        price <- priceOnly("AMD")
        _ <- putStrLn("AMD price: " + price)
        q <- quote("AMD")
        _ <- putStrLn("AMD quote: " + q)
        qf <- quoteField("AMD", Quote.IexAskSize, Some(true))
        _ <- putStrLn("AMD quote iex ask size: " + qf)
        vol <- volumeByVenue("AMD")
        _ <- putStrLn("AMD volume by venue: " + vol)
        sma <- technicalIndicator("AMD", TechnicalIndicators.Sma(5), HistoricalPrices._6m)
        _ <- putStrLn("AMD SMA 6m: " + sma)
        bbands <- technicalIndicator("AMD", TechnicalIndicators.Bbands(20, 2), HistoricalPrices.Ytd)
        _ <- putStrLn("AMD bbands ytd: " + bbands)
        divs <- dividends("IBM", Dividends._5y)
        _ <- putStrLn("IBM dividends ytd: " + divs)
        bk <- book("IBM")
        _ <- putStrLn("IBM book: " + bk)
      } yield ()
    }

}
