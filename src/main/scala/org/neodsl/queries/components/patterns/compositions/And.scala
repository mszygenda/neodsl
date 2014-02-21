package org.neodsl.queries.components.patterns.compositions

import org.neodsl.queries.domain._
import org.neodsl.queries.components.patterns.{PatternTripple}

case class And[U >: Null <: Node[U], V >: Null <: Node[V]](head: PatternTripple[U, V], tail: PatternComposition) extends PatternComposition {
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](pattern: => PatternTripple[U, V]) = {
    And(pattern, this)
  }
}




