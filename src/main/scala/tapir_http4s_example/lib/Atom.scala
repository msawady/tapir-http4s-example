package tapir_http4s_example.lib

trait Atom[T] {
  type Value = T

  val value: Value
}
