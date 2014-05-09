package org.neodsl.queries.domain


case class Relation[+T >: Null <: TypedNode[T], +U >: Null <: TypedNode[U]](name: String, direction: RelationDirection) {
  override def toString: String = {
    direction match {
      case --> => "-[:%s]->" format name
      case <-- => "<-[:%s]-" format name
      case -- => "-"
    }
  }
}
