package tapir_http4s_example.routes

import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder}
import sttp.tapir.CodecFormat.TextPlain
import sttp.tapir.EndpointIO.annotations.{endpointInput, path}
import sttp.tapir._
import sttp.tapir.json.circe._
import tapir_http4s_example.models.Task.{Status, TaskId}
import tapir_http4s_example.models.{Problem, Task}

object TaskRoutes {

  import codecs._
  val baseEndpoint: Endpoint[Unit, Unit, Problem, Unit, Any] = endpoint.in("task").errorOut(jsonBody[Problem])

  @endpointInput("task/{taskId}")
  case class GetTaskInput(@path taskId: TaskId)

  val getTaskEndpoint: Endpoint[Unit, GetTaskInput, Problem, Task, Any] =
    baseEndpoint.in(path[TaskId]("taskId")).mapInTo[GetTaskInput].out(jsonBody[Task])

  object codecs {
    // memo: utilize wrapped value
    implicit val taskIdSchema: Schema[TaskId] = Schema(SchemaType.SString())
    implicit val taskIdCodec: Codec[String, TaskId, TextPlain] = Codec.string.map(TaskId)(_.value)
    implicit val taskIdEncoder: Encoder[TaskId] = Encoder.instance(_.value.asJson)
    implicit val taskIdDecoder: Decoder[TaskId] = Decoder.instance(c => c.as[String].map(TaskId))

    // memo: utilize enum
    implicit val statusSchema: Schema[Status] = Schema(SchemaType.SString())
    implicit val statusEncoder: Encoder[Status] = Encoder.instance(s => s.asString.asJson)

    implicit val statusDecoder: Decoder[Status] = Decoder.instance { c =>
      c.as[String].map(Status.fromString)
    }

    implicit val problemSchema: Schema[Problem] = Schema.derived
    implicit val problemEncoder: Encoder[Problem] = deriveEncoder
    implicit val problemDecoder: Decoder[Problem] = deriveDecoder

    implicit val taskSchema: Schema[Task] = Schema.derived
    implicit val taskEncoder: Encoder[Task] = deriveEncoder
    implicit val taskDecoder: Decoder[Task] = deriveDecoder
  }
}
