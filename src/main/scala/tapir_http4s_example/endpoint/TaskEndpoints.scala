package tapir_http4s_example.endpoint

import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import sttp.tapir.CodecFormat.TextPlain
import sttp.tapir.EndpointIO.annotations.{endpointInput, path}
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import tapir_http4s_example.models.Task.TaskId
import tapir_http4s_example.models.{Problem, Task}

object TaskEndpoints {

  import codecs._
  val baseEndpoint: Endpoint[Unit, Unit, Problem, Unit, Any] = endpoint.in("task").errorOut(jsonBody[Problem])

  @endpointInput("task/{taskId}")
  case class GetTaskInput(@path taskId: TaskId)

  val getTaskEndpoint: Endpoint[Unit, GetTaskInput, Problem, Task, Any] =
    baseEndpoint.in(path[TaskId]("taskId")).mapInTo[GetTaskInput].out(jsonBody[Task])

  object codecs {

    // memo: utilize wrapped value
    implicit val taskIdSchema: Schema[TaskId] = Schema(SchemaType.SString())
    implicit val taskIdDecoder: Decoder[TaskId] = Decoder.instance(c => c.as[String].map(TaskId))
    implicit val taskIdEncoder: Encoder[TaskId] = Encoder.instance(_.value.asJson)
    implicit val taskIdCodec: Codec[String, TaskId, TextPlain] = Codec.string.map(TaskId)(_.value)
  }
}
