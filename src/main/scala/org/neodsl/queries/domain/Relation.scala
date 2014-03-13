package org.neodsl.queries.domain

import org.neodsl.queries.domain._

case class Relation[+T >: Null <: TypedNode[T], +U >: Null <: TypedNode[U]](name: String, direction: RelationDirection) {
  override def toString: String = {
    direction match {
      case --> => "-[:%s]->" format name
      case <-- => "<-[:%s]-" format name
      case -- => "-"
    }
  }
}
