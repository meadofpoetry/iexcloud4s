package com.iexcloudapi

import java.time.{LocalDate, YearMonth}
import java.util.concurrent.TimeUnit

import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import com.iexcloud4s.http._
import com.iexcloud4s.stocks.Prices
import zio._
import zio.clock._
import zio.console.{Console, putStrLn}
import zio.interop.catz._
import zio.logging.Logging

import scala.concurrent.ExecutionContext.Implicits

object Main extends App {

  def run(args: List[String]): ZIO[ZEnv, Nothing, ExitCode] = {
    val program = for {
      http4sClient <- makeHttpClient

      _ <- instantiateProgram(http4sClient)
    } yield ()

    program.foldM(
      e => putStrLn(s"Execution failed with: ${e.printStackTrace()}") *> ZIO.succeed(ExitCode.failure),
      _ => ZIO.succeed(ExitCode.success)
    )
  }

  type Http = Has[Service[Task]]

  private object Http {
    def call[E,R](f: Service[Task] => ZIO[Http, E, R]): ZIO[Http, E, R] =
      ZIO.accessM(s => f(s.get))
  }

  private def program: RIO[Http with Console with Clock, Unit] =
    RIO.access[Http](_.get).flatMap { implicit service =>
      for {
        start <- currentTime(TimeUnit.MILLISECONDS)
        _ <- putStrLn("Started...")
        result <- Prices.quote("aapl")
        finish <- currentTime(TimeUnit.MILLISECONDS)
        _ <- putStrLn(result.toString())
        _ <- putStrLn("Execution time: " + (finish - start))
      } yield ()
  }

  private def makeHttpClient: UIO[TaskManaged[Client[Task]]] =
    ZIO.runtime[Any].map { implicit rts =>
      BlazeClientBuilder
        .apply[Task](Implicits.global)
        .resource
        .toManaged
    }

  private def service: ZLayer[Has[Config] with Has[Client[Task]], Nothing, Http] =
    ZLayer.fromServices[Config, Client[Task], Service[Task]] { (config, http4sClient) =>
      com.iexcloud4s.http.service(config, http4sClient)
    }

  private def instantiateProgram(http4sClient: TaskManaged[Client[Task]]): RIO[ZEnv, Unit] = {
    val config =
      ZLayer.succeed(Config("Tpk_357fe0044aa9499f9f4532b4efa02455", sandboxed = true))
    val httpClientLayer = http4sClient.toLayer.orDie
    val serviceLayer = (config ++ httpClientLayer) >>> service

    program.provideSomeLayer[ZEnv](serviceLayer)
  }

}
