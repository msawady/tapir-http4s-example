package tapir_http4s_example.models

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}
import sttp.tapir.Schema.annotations.{description, validate}
import sttp.tapir.Validator
import tapir_http4s_example.lib.Atom
import tapir_http4s_example.models.Task.{TaskId, TaskStatus}

case class Task(
    @description("ID of task")
    taskId: TaskId,
    @description("Summery of task.")
    @validate(Validator.maxLength(100))
    summery: String,
    @description(
      """
        |Details of task.
        |
        |You can write details with new lines.
        |""".stripMargin)
    detail: String,
    @description("Status of task.")
    status: TaskStatus
)

object Task {

  case class TaskId(value: String) extends Atom[String]

  sealed abstract class TaskStatus(val value: String) extends StringEnumEntry

  object TaskStatus extends StringEnum[TaskStatus] with StringCirceEnum[TaskStatus] {

    case object Todo extends TaskStatus("TODO")
    case object Doing extends TaskStatus("DOING")
    case object Done extends TaskStatus("DONE")

    override def values: IndexedSeq[TaskStatus] = findValues
  }
}
