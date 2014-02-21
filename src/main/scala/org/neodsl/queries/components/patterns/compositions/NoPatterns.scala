package org.neodsl.queries.components.patterns.compositions

import org.neodsl.queries.domain.Node
import org.neodsl.queries.components.patterns.PatternTripple

case object NoPatterns extends PatternComposition {
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](buildPattern: => PatternTripple[U, V]) = {
    And(buildPattern, this)
  }
}