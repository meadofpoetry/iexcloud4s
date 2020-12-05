package com.iexcloud4s.http

import org.http4s.client.blaze.BlazeClientBuilder
import org.http4s.client.Client
import cats.syntax.all._
import cats.effect._
import com.iexcloud4s.http.ApiVersion
//import scodec.bits.LiteralSyntaxMacros.blackbox
import scala.concurrent.ExecutionContext

final case class InvalidToken(token: String) extends Exception("Bad token: " + token)
final case object TokenMismatchSandboxed extends Exception

sealed class ClientBuilder[F[_]] private (
  val apiToken: String,
  val version: ApiVersion,
  val sandboxed: Boolean,
  val clientBuilder: BlazeClientBuilder[F],
)(implicit protected val F: ConcurrentEffect[F]) {

  private def copy(
    apiToken: String = apiToken,
    version: ApiVersion = version,
    sandboxed: Boolean = sandboxed,
    clientBuilder: BlazeClientBuilder[F] = clientBuilder,
  ): ClientBuilder[F] =
    new ClientBuilder[F](
      apiToken = apiToken,
      version = version,
      sandboxed = sandboxed,
      clientBuilder = clientBuilder,
    ) {}

  def withToken(apiToken: String): ClientBuilder[F] =
    copy(apiToken = apiToken)

  def withVersion(version: ApiVersion): ClientBuilder[F] =
    copy(version = version)

  def withSandboxed(sandboxed: Boolean): ClientBuilder[F] =
    copy(sandboxed = sandboxed)

  def withClient(clientBuilder: BlazeClientBuilder[F]) =
    copy(clientBuilder = clientBuilder)

  def resource: Resource[F, IEXClient[F]] =
    validate match {
      case Left(e) => Resource.liftF(e.raiseError)
      case Right(_) => clientBuilder.resource.map  { client =>
        Http4s(apiToken, version, sandboxed, client)
      }
    }

  private def validate: Either[Exception, Unit] =
    if (!apiToken.startsWith("Tpk_")
      && !apiToken.startsWith("Tsk_")
      && !apiToken.startsWith("pk_")
      && !apiToken.startsWith("sk_"))
      Left(InvalidToken(apiToken))
    else if (! (sandboxed ^ !apiToken.startsWith("T")))
      Left(TokenMismatchSandboxed)
    else Right(())

}

object ClientBuilder {

  def apply[F[_]: ConcurrentEffect](executionContext: ExecutionContext): ClientBuilder[F] =
    new ClientBuilder[F](
      apiToken = "",
      version = V1,
      sandboxed = false,
      clientBuilder = BlazeClientBuilder.apply[F](executionContext)
    )

  def apply[F[_]: ConcurrentEffect](blazeClientBuilder: BlazeClientBuilder[F]): ClientBuilder[F] =
    new ClientBuilder[F](
      apiToken = "",
      version = V1,
      sandboxed = false,
      clientBuilder = blazeClientBuilder
    )

}
