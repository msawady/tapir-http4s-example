package tapir_http4s_example.lib

trait Atom[T] extends Atom0 {
  type Value = T

  val value: Value
}

trait Atom0 {
  type Value

  val value: Value
}
