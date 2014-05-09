package org.neodsl.dsl.patterns

import org.neodsl.queries.components.patterns.compositions.{And, PatternComposition}
import org.neodsl.dsl.Selfie
import org.neodsl.queries.domain.TypedNode
import org.neodsl.queries.components.patterns.PatternTriple
import org.neodsl.queries.components.conditions.Condition
import org.neodsl.dsl.queries.QueryBuilder

trait PatternCompositionExtensions extends Selfie[PatternComposition]{
  def and[U >: Null <: TypedNode[U], V >: Null <: TypedNode[V]](pattern: => PatternTriple[U, V]) = {
    And(pattern, self)
  }

  def where(condition: => Condition) = {
    new QueryBuilder(self, condition)
  }
}
