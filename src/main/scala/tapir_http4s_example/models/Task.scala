package tapir_http4s_example.models

import enumeratum.values.{StringCirceEnum, StringEnum, StringEnumEntry}
import tapir_http4s_example.models.Task.{TaskId, TaskStatus}

case class Task(
    taskId: TaskId,
    summery: String,
    detail: String,
    status: TaskStatus
)

object Task {

  case class TaskId(value: String)

  sealed abstract class TaskStatus(val value: String) extends StringEnumEntry

  object TaskStatus extends StringEnum[TaskStatus] with StringCirceEnum[TaskStatus]{

    case object Todo extends TaskStatus("TODO")
    case object Doing extends TaskStatus("DOING")
    case object Done extends TaskStatus("DONE")

    override def values: IndexedSeq[TaskStatus] = findValues
  }
}
