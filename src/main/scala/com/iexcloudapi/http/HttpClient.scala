package com.iexcloudapi.http

import io.circe.Decoder
import org.http4s.client.Client
import org.http4s.Uri
import zio._
import java.lang.module.Configuration

object HttpClient {

  type HttpClient = Has[Service]

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

  trait Service {
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
      (implicit d: Decoder[T]): Task[T]
  }

  def get[T](uri: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): RIO[HttpClient, T] =
    RIO.accessM[HttpClient](_.get.get(uri, parameters)(d))

  def http4s: ZLayer[Has[Config] with Has[Client[Task]], Nothing, HttpClient] =
    ZLayer.fromServices[Config, Client[Task], Service] { (config, http4sClient) =>
      Http4s(config, http4sClient)
    }

}
