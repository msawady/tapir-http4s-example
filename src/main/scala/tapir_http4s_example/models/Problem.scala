package tapir_http4s_example.models

case class Problem(title: String, detail: Option[String])

object Problem {
  def apply(title: String, detail: String): Problem = new Problem(title, Some(detail))
}
