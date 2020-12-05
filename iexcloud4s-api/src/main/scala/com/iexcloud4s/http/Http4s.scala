package com.iexcloud4s.http

import io.circe.Decoder
import org.http4s.Uri
import org.http4s.circe._
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.{Client, UnexpectedStatus}
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.Status
import cats.effect.Sync
import cats.syntax.all._

private[http] final case class Http4s[F[_]: Sync](
  val apiToken: String,
  val version: ApiVersion,
  val sandboxed: Boolean,
  val client: Client[F],
) extends IEXClient[F] with Http4sClientDsl[F] {

  lazy val root = {
    import Uri._
    val ver = version match {
      case V1 => "v1"
      case Stable => "stable"
      case Beta => "beta"
    }
    val typ = if (sandboxed) {
      "sandbox"
    } else {
      "cloud"
    }
    val auth = Some(Authority(host = RegName(s"$typ.iexapis.com")))
    Uri(scheme = Some(Scheme.https), authority = auth)
      .addPath(ver)
      .withQueryParam("token", apiToken)
  }

  def get[T](resource: String, parameters: Parameters)
    (implicit d: Decoder[T]): F[T] = {
    val url = root
      .addPath(resource)
      .withQueryParams(parameters.toMap)

    client.get[T](url) {
      case Status.Ok(res) =>
        res.as[T]
      case res =>
        res.bodyText.compile.string
          .flatMap(IEXTradingFailure(_).raiseError[F, T])
      }

/*
    get[Either[IEXTradingError,T]](url) {
      case Status.Ok(r) =>
        r.attemptAs[T].leftMap(err => IEXTradingError(err.message, 200)).value
      case r =>
        Functor[F].map(r.as[String](text))((f: String) =>
          Either.left(IEXTradingError(f,0)))
    }
 */
  }

}
