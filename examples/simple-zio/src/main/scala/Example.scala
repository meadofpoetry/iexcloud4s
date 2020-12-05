package com.iexcloudapi

import zio._
import zio.clock._
import zio.console.{Console, putStrLn}
import zio.interop.catz._
import zio.logging.Logging

import com.iexcloud4s.http._
import com.iexcloud4s.stocks._

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
        amdBeta <- keyStats("AMD", KeyStats.Beta)
        _ <- putStrLn("AMD Beta: " + amdBeta)
        advStats <- advancedStats("AMD")
        _ <- putStrLn("AMD adv: " + advStats)
        comp <- company("XLNX")
        _ <- putStrLn("XLNX info: " + comp)
        earn <- earnings("AMD")
        _ <- putStrLn("AMD earn: " + earn)
        earnea <- earningsField("AMD", Earnings.YearAgo)
        _ <- putStrLn("AMD earn year ago: " + earnea)
      } yield ()
    }

}
