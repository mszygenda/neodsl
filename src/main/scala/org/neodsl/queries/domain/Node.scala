package org.neodsl.queries.domain

trait Node[+T >: Null <: Node[T]] {
  val id: Option[Long]
}
