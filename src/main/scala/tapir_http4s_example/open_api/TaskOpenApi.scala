package tapir_http4s_example.open_api
import sttp.apispec.openapi.{Contact, Info, License, OpenAPI}
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import tapir_http4s_example.endpoint.TaskEndpoints

import scala.collection.immutable.ListMap

object TaskOpenApi {

  val GenerateOpenApiSpec: OpenAPI = {
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
               | You can write more description with new lines.
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
    docs
  }
}
