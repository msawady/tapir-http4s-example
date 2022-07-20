package tapir_http4s_example.endpoint

import io.circe.generic.auto._
import io.circe.{Decoder, Encoder}
import shapeless._
import sttp.tapir.CodecFormat.TextPlain
import sttp.tapir.EndpointIO.annotations.{endpointInput, path}
import sttp.tapir._
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe._
import tapir_http4s_example.lib.{Atom, Atom0}
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

    implicit def stringAtomSchema[T <: Atom[String]]: Schema[T] = Schema(SchemaType.SString())

    implicit def stringAtomCodec[T <: Atom[String]](
        implicit gen: Generic.Aux[T, String :: HNil]
    ): Codec[String, T, TextPlain] = Codec.string.map(v => gen.from(v :: HNil))(_.value)

    implicit def atomDecoder[T <: Atom0](
        implicit
        gen: Generic.Aux[T, T#Value :: HNil],
        valueDecoder: Decoder[T#Value]
    ): Decoder[T] = valueDecoder.map(x => gen.from(x :: HNil))

    implicit def atomEncoder[T <: Atom0](
        implicit valueEncoder: Encoder[T#Value]
    ): Encoder[T] = valueEncoder.contramap(_.value)

  }
}
