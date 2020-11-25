package com.iexcloudapi.http

import io.circe.Decoder
import org.http4s.client.Client
import org.http4s.Uri
import cats.effect.Sync

object HttpClient {

  sealed trait ApiVersion
  final case object V1 extends ApiVersion
  final case object Stable extends ApiVersion
  final case object Beta extends ApiVersion

  // TODO check token validity for sanboxed env
  final case class Config(
    apiToken: String,
    version: ApiVersion = V1,
    sandboxed: Boolean = false
  )

  trait Service[F[_]] {
    protected def rootUrl(config: Config) = {
      import Uri._
      val ver = config.version match {
        case V1 => "v1"
        case Stable => "stable"
        case Beta => "beta"
      }
      val typ = if (config.sandboxed) {
        "sandbox"
      } else {
        "cloud"
      }
      val auth = Some(Authority(host = RegName(s"$typ.iexapis.com")))
      Uri(scheme = Some(Scheme.https), authority = auth)
        .addPath(ver)
        .withQueryParam("token", config.apiToken)
    }

    def get[T](uri: String, parameters: Map[String, String])
      (implicit d: Decoder[T]): F[T]
  }

  def client[F[_]: Sync](config: HttpClient.Config, client: Client[F]): Service[F] =
    com.iexcloudapi.http.Http4s(config, client)

}
