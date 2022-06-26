package tapir_http4s_example

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = TaskHttp4sServer.stream.compile.drain.as(ExitCode.Success)
}
