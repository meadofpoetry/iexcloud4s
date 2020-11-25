package com.iexcloudapi

import java.time.{LocalDate, YearMonth}
import java.util.concurrent.TimeUnit

import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import com.iexcloudapi.http.HttpClient
import com.iexcloudapi.stocks.Prices
import zio._
import zio.clock._
import zio.console.putStrLn
import zio.interop.catz._
import zio.logging.Logging

import scala.concurrent.ExecutionContext.Implicits

object Main extends App {

  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = {
    val program = for {
      http4sClient <- makeHttpClient

      _ <- makeProgram(http4sClient)
    } yield ()

    program.foldM(
      e => putStrLn(s"Execution failed with: ${e.printStackTrace()}") *> ZIO.succeed(ExitCode.failure),
      _ => ZIO.succeed(ExitCode.success)
    )
  }

  private def makeHttpClient: UIO[TaskManaged[Client[Task]]] =
    ZIO.runtime[Any].map { implicit rts =>
      BlazeClientBuilder
        .apply[Task](Implicits.global)
        .resource
        .toManaged
    }

  private def makeProgram(http4sClient: TaskManaged[Client[Task]]): RIO[ZEnv, Unit] = {
    val config =
      ZLayer.succeed(HttpClient.Config("Tpk_357fe0044aa9499f9f4532b4efa02455", sandboxed = true))
    val httpClientLayer = http4sClient.toLayer.orDie
    val http4sClientLayer = (config ++ httpClientLayer) >>> HttpClient.http4s

    val program = for {
      start <- currentTime(TimeUnit.MILLISECONDS)
      _ <- putStrLn("Started...")
      result <- Prices.quote("aapl")

      finish <- currentTime(TimeUnit.MILLISECONDS)
      _ <- putStrLn(result.toString())
      _ <- putStrLn("Execution time: " + (finish - start))
    } yield ()

    program.provideSomeLayer[ZEnv](http4sClientLayer)
  }

}
