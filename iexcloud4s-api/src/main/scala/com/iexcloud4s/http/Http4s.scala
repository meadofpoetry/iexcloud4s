package com.iexcloud4s.http

import io.circe.Decoder
import org.http4s.Uri
import org.http4s.circe._
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import cats.effect.Sync

private[http] final case class Http4s[F[_]: Sync](
  config: Config, client: Client[F])
    extends Service[F] with Http4sClientDsl[F] {

  lazy val root = rootUrl(config)

  def get[T](resource: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): F[T] = {
    val url = root
      .addPath(resource)
      .withQueryParams(parameters)
  
    client.expect[T](url)
  }

}
