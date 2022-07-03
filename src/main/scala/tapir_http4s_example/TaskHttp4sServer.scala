package tapir_http4s_example

import cats.data.Kleisli
import cats.effect.{IO, Resource}
import cats.syntax.all._
import com.comcast.ip4s._
import fs2.Stream
import org.http4s.{HttpRoutes, Request, Response}
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.Logger
import sttp.tapir.server.http4s.Http4sServerInterpreter
import tapir_http4s_example.handler.GetTaskHandler
import tapir_http4s_example.endpoint.TaskEndpoints

object TaskHttp4sServer {

  // memo: use DI in production
  val getTaskHandler = new GetTaskHandler

  val routes: HttpRoutes[IO] = Http4sServerInterpreter[IO]().toRoutes(
    TaskEndpoints.getTaskEndpoint.serverLogic(getTaskHandler.getTask)
  )
  val httpApp: Kleisli[IO, Request[IO], Response[IO]] = routes.orNotFound

  def stream: Stream[IO, Nothing] = {
    val withLogger = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)

    for {

      // Combine Service Routes into an HttpApp.
      // Can also be done via a Router if you
      // want to extract a segments not checked
      // in the underlying routes.

      // With Middlewares in place

      exitCode <- Stream.resource(
        EmberServerBuilder.default[IO]
          .withHost(ipv4"0.0.0.0")
          .withPort(port"8080")
          .withHttpApp(withLogger)
          .build >>
          Resource.eval(IO.never)
      )
    } yield exitCode
  }.drain

}
