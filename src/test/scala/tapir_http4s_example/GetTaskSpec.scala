package tapir_http4s_example

import cats.effect.IO
import io.circe.Json
import io.circe.syntax.KeyOps
import munit.CatsEffectSuite
import org.http4s.circe.CirceEntityCodec.circeEntityDecoder
import org.http4s.client.Client
import org.http4s.implicits.http4sLiteralsSyntax
import org.http4s.{Method, Request}
import tapir_http4s_example.handler.GetTaskHandler
import tapir_http4s_example.models.Task.TaskId
import tapir_http4s_example.routes.TaskRoutes.GetTaskInput

class GetTaskSpec extends CatsEffectSuite {
  private val client = Client.fromHttpApp(TaskHttp4sServer.httpApp)

  test("get task") {
    val request: Request[IO] = Request(method = Method.GET, uri = uri"/task/task1")
    val actual: IO[Json] = client.expect[Json](request)
    val mockTask = GetTaskHandler.mockTask(GetTaskInput(TaskId("task1")))
    val expected = Json.obj(
      "taskId" := mockTask.taskId.value,
      "summery" := mockTask.summery,
      "detail" := mockTask.detail,
      "status" := mockTask.status.asString
    )
    assertIO(actual, expected)
  }
  test("get task with mock failure") {
    val actual: IO[Json] = client.get(uri"/task/FAIL")(res => {
      assert(res.status.code == 400)
      res.as[Json]
    })
    val mockProblem = GetTaskHandler.mockProblem
    val expected = Json.obj(
      "title" := mockProblem.title,
      "detail" := mockProblem.detail
    )
    assertIO(actual, expected)
  }
}
