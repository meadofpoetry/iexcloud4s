package com.iexcloud4s

import io.circe.Decoder
import org.http4s.client.Client
import org.http4s.Uri
import cats.effect.Sync

package object http {

  sealed trait ApiVersion
  final case object V1 extends ApiVersion
  final case object Stable extends ApiVersion
  final case object Beta extends ApiVersion

  final case class IEXTradingFailure(
    private val message: String,
    private val cause: Throwable = None.orNull)
      extends Exception(message)

  final class Parameters(args: List[(String, Option[Any])]) {
    private[http] def toMap: Map[String, String] =
      args.flatMap {
        case (_, None) => None
        case (k, Some(v)) => Some(k -> v.toString())
      }.toMap
  }

  object Parameters {
    val empty =
      new Parameters(Nil)
    def apply(args: (String, Option[Any])*): Parameters =
      new Parameters(args.toList)
  }

  trait IEXClient[F[_]] {

    def get[T](uri: String, parameters: Parameters)
      (implicit d: Decoder[T]): F[T]

  }

}
