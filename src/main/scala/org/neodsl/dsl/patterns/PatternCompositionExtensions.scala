package org.neodsl.dsl.patterns

import org.neodsl.queries.components.patterns.compositions.{NoPatterns, And, PatternComposition}
import org.neodsl.dsl.Selfie
import org.neodsl.queries.domain.Node
import org.neodsl.queries.components.patterns.PatternTripple
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.dsl.queries.QueryBuilder

trait PatternCompositionExtensions extends Selfie[PatternComposition]{
  def and[U >: Null <: Node[U], V >: Null <: Node[V]](pattern: => PatternTripple[U, V]) = {
    And(pattern, self)
  }

  def where(condition: => Condition) = {
    new QueryBuilder(self, condition)
  }
}