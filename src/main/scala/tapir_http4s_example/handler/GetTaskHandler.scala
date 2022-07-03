package tapir_http4s_example.handler

import cats.effect.IO
import tapir_http4s_example.models.{Problem, Task}
import tapir_http4s_example.models.Task.Status
import tapir_http4s_example.endpoint.TaskEndpoints.GetTaskInput

class GetTaskHandler {

  def mockTask(in: GetTaskInput): Task = Task(
    taskId = in.taskId,
    summery = "mock res",
    detail = "this is mock response detail.",
    status = Status.Done
  )
  val mockProblem: Problem = Problem("failing", "this is mock failure.")

  def getTask(in: GetTaskInput): IO[Either[Problem, Task]] = {
    IO.pure {
      if (in.taskId.value == "FAIL") {
        Left(mockProblem)
      } else {
        Right(
          mockTask(in)
        )
      }
    }
  }
}

object GetTaskHandler {

  def mockTask(in: GetTaskInput): Task = Task(
    taskId = in.taskId,
    summery = "mock res",
    detail = "this is mock response detail.",
    status = Status.Done
  )
  val mockProblem: Problem = Problem("failing", "this is mock failure.")

}
