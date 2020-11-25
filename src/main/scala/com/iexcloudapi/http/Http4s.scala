package com.iexcloudapi.http

import io.circe.Decoder
import org.http4s.Uri
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.Client
import org.http4s.client.dsl.Http4sClientDsl
import zio._
import zio.interop.catz._

private[http] final case class Http4s(config: HttpClient.Config, client: Client[Task])
    extends HttpClient.Service with Http4sClientDsl[Task] {

  lazy val root = rootUrl(config)

  def get[T](resource: String, parameters: Map[String, String])
    (implicit d: Decoder[T]): Task[T] = {
    val url = root
      .addPath(resource)
      .withQueryParams(parameters)
  
    client.expect[T](url)
  }

}
