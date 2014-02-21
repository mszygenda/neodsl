package org.neodsl.dsl.patterns

import org.neodsl.queries.components.patterns.compositions.{And, PatternComposition}
import org.neodsl.dsl.Selfie
import org.neodsl.queries.domain.Node
import org.neodsl.queries.components.patterns.PatternTripple

trait PatternCompositionExtensions extends Selfie[PatternComposition]{
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](pattern: => PatternTripple[U, V]) = {
    And(pattern, self)
  }
}
