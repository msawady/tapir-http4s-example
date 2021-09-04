package tapir_http4s_example

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    Tapirhttp4sexampleServer.stream[IO].compile.drain.as(ExitCode.Success)
}
