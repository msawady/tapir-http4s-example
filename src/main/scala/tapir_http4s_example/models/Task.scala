package tapir_http4s_example.models

import tapir_http4s_example.models.Task.{Status, TaskId}

case class Task(
    taskId: TaskId,
    summery: String,
    detail: String,
    status: Status
)

object Task {

  case class TaskId(value: String)

  sealed trait Status {
    val asString: String
  }

  object Status {

    def fromString(str: String): Status = {
      str.toUpperCase() match {
        case Todo.`asString`  => Todo
        case Doing.`asString` => Doing
        case Done.`asString`  => Todo
        // memo: improve error handling
        case _ => throw new RuntimeException("unsupported value")
      }
    }

    case object Todo extends Status {
      override val asString: String = "TODO"
    }

    case object Doing extends Status {
      override val asString: String = "DOING"
    }

    case object Done extends Status {
      override val asString: String = "DONE"
    }
  }
}
