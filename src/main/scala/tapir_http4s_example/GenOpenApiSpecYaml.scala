package tapir_http4s_example

import cats.effect.{ExitCode, IO, IOApp}
import sttp.apispec.openapi.circe.yaml._
import tapir_http4s_example.open_api.TaskOpenApi

object GenOpenApiSpecYaml extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val docs = TaskOpenApi.GenerateOpenApiSpec

    IO.pure(println(docs.toYaml)).as(ExitCode.Success)
  }
}
