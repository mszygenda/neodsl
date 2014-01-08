package org.neodsl.dsl.domain

case class Relation[T >: Null <: Node[T], U >: Null <: Node[T]](name: String, direction: RelationDirection) {
  override def toString: String = {
    direction match {
      case --> => "-[:%s]->" format name
      case <-- => "<-[:%s]-" format name
      case -- => "-"
    }
  }
}
