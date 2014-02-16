package org.neodsl.dsl.domain

import org.neodsl.dsl.patterns._
import org.neodsl.dsl.domain.-->
import org.neodsl.dsl.patterns.PatternTrippleElement
import org.neodsl.dsl.patterns.RelationElement
import org.neodsl.dsl.patterns.NodeElement

case class Relation[T >: Null <: Node[T], U >: Null <: Node[U]](name: String, direction: RelationDirection) {
  override def toString: String = {
    direction match {
      case --> => "-[:%s]->" format name
      case <-- => "<-[:%s]-" format name
      case -- => "-"
    }
  }
}
