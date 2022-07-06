package tapir_http4s_example

import cats.effect.{ExitCode, IO, IOApp}
import sttp.apispec.openapi.circe.yaml._
import sttp.apispec.openapi.{Contact, Info, License}
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import tapir_http4s_example.endpoint.TaskEndpoints

import scala.collection.immutable.ListMap

object GenOpenApiSpec extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val endpoints = List(TaskEndpoints.getTaskEndpoint)
    val docs = OpenAPIDocsInterpreter()
      .toOpenAPI(
        endpoints,
        Info(
          title = "My Todo App",
          version = "1.0.0",
          description = Some(
            """| This is API spec for My App.
               |
               | We can write description contains new lines.
               |""".stripMargin
          ),
          termsOfService = Some("see: https://my-company.com/terms_of_service"),
          contact = Some(
            Contact(
              name = Some("developer support in my-company"),
              email = Some("dev-support@my-company.com")
            )
          ),
          license = Some(License(name = "MIT", url = None)),
          extensions = ListMap.empty
        )
      )

    IO.pure(println(docs.toYaml)).as(ExitCode.Success)
  }
}
