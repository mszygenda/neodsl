package org.neodsl.queries.domain

import org.neodsl.queries.domain._

case class Relation[+T >: Null <: Node[T], +U >: Null <: Node[U]](name: String, direction: RelationDirection) {
  override def toString: String = {
    direction match {
      case --> => "-[:%s]->" format name
      case <-- => "<-[:%s]-" format name
      case -- => "-"
    }
  }
}
